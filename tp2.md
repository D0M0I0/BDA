# Exercice 1
## 1.1
Afficher le nom du département qui a le budget le plus élevé.
```postgresql
SELECT dept_name
FROM department
WHERE budget IN (SELECT MAX(budget) FROM department);
``` 

Sortie de la requête :
```bash
 dept_name 
-----------
 Finance
(1 row)
```

## 1.2
Afficher les salaires et les noms des enseignants qui gagnent plus que le salaire moyen.
```postgresql
SELECT A.name, A.salary
FROM teacher as A
WHERE A.salary > (SELECT AVG(B.salary)
FROM teacher as B);
```

Sortie de la requête :
```bash
   name   |  salary  
----------+----------
 Wu       | 90000.00
 Einstein | 95000.00
 Gold     | 87000.00
 Katz     | 75000.00
 Singh    | 80000.00
 Brandt   | 92000.00
 Kim      | 80000.00
(7 rows)
```

## 1.3
Pour chaque enseignant, afficher tous les étudiants qui ont suivi plus de deux cours dispensés par cet enseignant ainsi que le nombre total de cours suivis par chaque étudiant, en utilisant la clause HAVING.
```postgresql
SELECT teacher.name, student.name, COUNT(*)
FROM teacher, student, takes, teaches
WHERE teacher.id = teaches.id
    AND student.id = takes.id
    AND (takes.course_id, takes.sec_id, takes.year, takes.semester) = (teaches.course_id, teaches.sec_id, teaches.year, teaches.semester)
GROUP BY teacher.name, student.name HAVING COUNT(*) >= 2;
```

Sortie de la requête :
```bash
    name    |   name   | count 
------------+----------+-------
 Crick      | Tanaka   |     2
 Katz       | Levy     |     2
 Srinivasan | Bourikas |     2
 Srinivasan | Shankar  |     3
 Srinivasan | Zhang    |     2
(5 rows)
```

## 1.4
Pour chaque enseignant, afficher tous les étudiants qui ont suivi plus de deux cours dispensés
par cet enseignant ainsi que le nombre total de cours suivis par chaque étudiant, sans utiliser
la clause HAVING.

```postgresql
SELECT T.teachername, T.studentname, T.totalcount
FROM (SELECT teacher.name as teachername, student.name as studentname, COUNT(*) as totalcount
FROM teacher, student, takes, teaches
WHERE teacher.id = teaches.id 
    AND student.id = takes.id
    AND (takes.course_id, takes.sec_id, takes.year, takes.semester) = (teaches.course_id, teaches.sec_id, teaches.year, teaches.semester)
GROUP BY teacher.name, student.name) as T
WHERE T.totalcount >= 2 ORDER BY T.teachername;
```

Sortie de la requête :
```bash
teachername | studentname | totalcount
-------------+-------------+------------
Crick       | Tanaka      |          2
Katz        | Levy        |          2
Srinivasan  | Bourikas    |          2
Srinivasan  | Shankar     |          3
Srinivasan  | Zhang       |          2
(5 rows)
```

## 1.5
Afficher les identifiants et les noms des étudiants qui n’ont pas suivi de cours avant 2010.
```postgresql
(SELECT student.id, student.name FROM student)
EXCEPT
(SELECT student.id, student.name
FROM student, takes
WHERE student.id = takes.id
    AND takes.year < 2009);
```

Sortie de la requête :
```bash
  id   |   name   
-------+----------
 00128 | Zhang
 55739 | Sanchez
 45678 | Levy
 98988 | Tanaka
 19991 | Brandt
 23121 | Chavez
 98765 | Bourikas
 76653 | Aoi
 12345 | Shankar
 54321 | Williams
 70557 | Snow
 44553 | Peltier
 76543 | Brown
(13 rows)
```

## 1.6
Afficher tous les enseignants dont les noms commencent par E.
```postgresql
SELECT * FROM teacher WHERE name LIKE 'E%';
```

Sortie de la requête :
```bash
  id   |   name   | dept_name |  salary  
-------+----------+-----------+----------
 22222 | Einstein | Physics   | 95000.00
 32343 | El Said  | History   | 60000.00
(2 rows)
```
## 1.7
Afficher les salaires et les noms des enseignants qui perçoivent le quatrième salaire le plus élevé.
```postgresql
SELECT name
FROM teacher as T1
WHERE 3 =
(SELECT COUNT(DISTINCT T2.salary) FROM teacher as T2
WHERE T2.salary > T1.salary);
```

Sortie de la requête :
```bash
 name 
------
 Gold
(1 row)
```

## 1.8
Afficher les noms et les salaires des trois enseignants qui perçoivent les salaires les moins élèves.
Les afficher par ordre décroissant.
```postgresql
SELECT T1.name, T1.salary
FROM teacher as T1
WHERE 2 >= (
SELECT COUNT(DISTINCT T2.salary) FROM teacher as T2
WHERE T2.salary < T1.salary)
ORDER BY T1.salary ASC;
```

Sortie de la requête :
```bash
   name    |  salary  
-----------+----------
 Mozart    | 40000.00
 El Said   | 60000.00
 Califieri | 62000.00
(3 rows)
```

## 1.9
Afficher les noms des étudiants qui ont suivi un cours en automne 2009, en utilisant la clause
IN.
```postgresql
SELECT S.name
FROM student as S
WHERE ('Fall', 2009) IN (SELECT semester, year
FROM takes
WHERE takes.id = S.id);
```

Sortie de la requête :
```bash
   name   
----------
 Zhang
 Shankar
 Peltier
 Levy
 Williams
 Brown
 Bourikas
(7 rows)
```
## 1.10
Afficher les noms des étudiants qui ont suivi un cours en automne 2009, en utilisant la clause
SOME.
```postgresql
SELECT S.name
FROM student as S
WHERE ('Fall', 2009) = ANY (SELECT semester, year
FROM takes
WHERE takes.id = S.id);
```

Sortie de la requête :
```bash
   name   
----------
 Zhang
 Shankar
 Peltier
 Levy
 Williams
 Brown
 Bourikas
(7 rows)
```

## 1.11
Afficher les noms des étudiants qui ont suivi un cours en automne 2009, en utilisant la jointure
naturelle (NATURAL INNER JOIN).
```postgresql
SELECT DISTINCT student.name
FROM takes NATURAL INNER JOIN student
WHERE takes.semester = 'Fall' AND takes.year = 2009;
```

Sortie de la requête :
```bash
   name   
----------
 Bourikas
 Brown
 Levy
 Peltier
 Shankar
 Williams
 Zhang
(7 rows)
```

## 1.12
Afficher les noms des étudiants qui ont suivi un cours en automne 2009, en utilisant la clause
EXISTS.
```postgresql
SELECT name
FROM student
WHERE EXISTS (SELECT * 
FROM takes
WHERE takes.id = student.id
    AND semester = 'Fall'
    AND year = 2009);
```
Sortie de la requête :
```bash
   name   
----------
 Shankar
 Bourikas
 Brown
 Levy
 Peltier
 Zhang
 Williams
(7 rows)
```

## 1.13
Afficher toutes les paires des étudiants qui ont suivi au moins un cours ensemble.
```postgresql
SELECT A.name , B.name
FROM (student NATURAL INNER JOIN takes) as A,
(student NATURAL INNER JOIN takes) as B
WHERE A.course_id = B.course_id
    AND A.sec_id = B.sec_id
    AND A.year = B.year
    AND A.semester = B.semester
    AND A.id <> B.id
    AND A.name < B.name
ORDER BY A.name, B.name;
```

Sortie de la requête :
```bash
   name   |   name   
----------+----------
 Bourikas | Brown
 Bourikas | Levy
 Bourikas | Shankar
 Bourikas | Shankar
 Bourikas | Williams
 Bourikas | Zhang
 Brown    | Levy
 Brown    | Shankar
 Brown    | Williams
 Brown    | Zhang
 Levy     | Shankar
 Levy     | Williams
 Levy     | Zhang
 Shankar  | Williams
 Shankar  | Williams
 Shankar  | Zhang
 Shankar  | Zhang
 Williams | Zhang
(18 rows)
```

## 1.14
Afficher pour chaque enseignant, qui a effectivement assuré un cours, le nombre total d’étudiants
qui ont suivi ses cours. Si un étudiant a suivi deux cours différents avec le même enseignant, on
le compte deux fois. Trier le résultat par ordre décroissant.
```postgresql
SELECT teacher.name, COUNT(*)
FROM (takes INNER JOIN teaches USING (course_id, sec_id, semester, year))
INNER JOIN teacher ON teaches.id = teacher.id
GROUP BY teacher.name, teacher.id ORDER BY COUNT(*) DESC;
```
Sortie de la requête :
```bash
    name    | count 
------------+-------
 Srinivasan |    10
 Brandt     |     3
 Katz       |     2
 Crick      |     2
 El Said    |     1
 Kim        |     1
 Wu         |     1
 Mozart     |     1
 Einstein   |     1
(9 rows)
```

## 1.15
Afficher pour chaque enseignant, même s’il n’a pas assuré de cours, le nombre total d’étudiants
qui ont suivi ses cours. Si un étudiant a suivi deux fois un cours avec le même enseignant, on le
compte deux fois. Trier le résultat par ordre décroissant.
```postgresql
SELECT teacher.name, COUNT(course_id)
FROM (takes INNER JOIN teaches USING (course_id, sec_id, semester, year))
RIGHT OUTER JOIN teacher ON teaches.id = teacher.id
GROUP BY teacher.name, teacher.id ORDER BY COUNT(course_id) DESC;
```

Sortie de la requête :
```bash
    name    | count 
------------+-------
 Srinivasan |    10
 Brandt     |     3
 Katz       |     2
 Crick      |     2
 Einstein   |     1
 Kim        |     1
 Mozart     |     1
 El Said    |     1
 Wu         |     1
 Singh      |     0
 Gold       |     0
 Califieri  |     0
(12 rows)
```

## 1.16
Pour chaque enseignant, afficher le nombre total de grades A qu’il a attribué.
```postgresql
WITH mystakes
(id, course_id, sec_id, semester, year, grade) AS
(SELECT id, course_id, sec_id, semester, year, grade
FROM takes
WHERE grade = 'A')
SELECT teacher.name, COUNT(course_id)
FROM (mystakes INNER JOIN teaches USING (course_id, sec_id, semester, year))
RIGHT OUTER JOIN teacher ON teaches.id = teacher.id
GROUP BY teacher.name, teacher.id ORDER BY COUNT(course_id) DESC;
```
Sortie de la requête :
```bash
    name    | count 
------------+-------
 Srinivasan |     4
 Brandt     |     2
 Crick      |     1
 Califieri  |     0
 Einstein   |     0
 Wu         |     0
 Kim        |     0
 Mozart     |     0
 El Said    |     0
 Katz       |     0
 Singh      |     0
 Gold       |     0
(12 rows)
```

## 1.17
Afficher toutes les paires enseignant-élèves où un élève a suivi le cours de l’enseignant, ainsi que
le nombre de fois que cet élève a suivi un cours dispensé par cet enseignant.
```postgresql
SELECT teacher.name, student.name , COUNT(*) FROM (teacher NATURAL JOIN teaches)
INNER JOIN
(takes NATURAL JOIN student) USING
(course_id, sec_id, semester, year) GROUP BY teacher.name, student.name;
```
Sortie de la requête :
```bash
    name    |   name   | count 
------------+----------+-------
 Brandt     | Brown    |     1
 Brandt     | Shankar  |     1
 Brandt     | Williams |     1
 Crick      | Tanaka   |     2
 Einstein   | Peltier  |     1
 El Said    | Brandt   |     1
 Katz       | Levy     |     2
 Kim        | Aoi      |     1
 Mozart     | Sanchez  |     1
 Srinivasan | Bourikas |     2
 Srinivasan | Brown    |     1
 Srinivasan | Levy     |     1
 Srinivasan | Shankar  |     3
 Srinivasan | Williams |     1
 Srinivasan | Zhang    |     2
 Wu         | Chavez   |     1
(16 rows)
```

## 1.18
Afficher toutes les paires enseignant-élève où un élève a suivi au moins deux cours dispensés par
l’enseignant en question
```postgresql
SELECT x.tn, x.sn
FROM (SELECT teacher.name as tn, student.name as sn, COUNT(*) as tc
FROM (teacher NATURAL JOIN teaches) INNER JOIN (takes NATURAL JOIN student) USING (course_id, sec_id, semester, year)
GROUP BY teacher.name, student.name) as x
WHERE tc >= 2;
```

Sortie de la requête :
```bash
     tn     |    sn    
------------+----------
 Crick      | Tanaka
 Katz       | Levy
 Srinivasan | Bourikas
 Srinivasan | Shankar
 Srinivasan | Zhang
(5 rows)
```
