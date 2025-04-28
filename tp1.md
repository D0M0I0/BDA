# Exercice 1
## 1.1
```oraclesqlplus
create table section
    (
        course_id       varchar(8),
        sec_id          varchar(8),
        semester        varchar(8)
            check(semester in ('Fall', 'Winter', 'Spring', 'Summer')),
        year            numeric(4,0),
        building        varchar(15),
        room_number     varchar(8),
        time_slot_id    varchar(8),
        primary key(course_id, sec_id, semester, year),
        foreign key (course_id) references course,
        foreign key (building, room_number) references classroom
    );
```

## 1.4
```oraclesqlplus
insert into course values ('BIO-101', 'Intro. to Biology', 'Biology', '4');
```

# Exercice 2
## 2.1
Afficher la structure de la relation section et son contenu (cours proposés).
```oraclesqlplus
DESC section;
SELECT * FROM section;
```

Sortie de la commande DESC (´\d' sur PostgreSQL):
```bash
Table "public.section"
Column    |         Type          | Collation | Nullable | Default 
--------------+-----------------------+-----------+----------+---------
 course_id    | character varying(8)  |           | not null | 
 sec_id       | character varying(8)  |           | not null | 
 semester     | character varying(8)  |           | not null | 
 year         | numeric(4,0)          |           | not null | 
 building     | character varying(15) |           |          | 
 room_number  | character varying(8)  |           |          | 
 time_slot_id | character varying(8)  |           |          | 
Indexes:
    "section_pkey" PRIMARY KEY, btree (course_id, sec_id, semester, year)
Check constraints:
    "section_semester_check" CHECK (semester::text = ANY (ARRAY['Fall'::character varying, 'Winter'::character varying, 'Spring'::character varying, 'Summer'::character varying]::text[]))
Foreign-key constraints:
    "section_building_room_number_fkey" FOREIGN KEY (building, room_number) REFERENCES classroom(building, room_number)
    "section_course_id_fkey" FOREIGN KEY (course_id) REFERENCES course(course_id)
Referenced by:
    TABLE "takes" CONSTRAINT "takes_course_id_sec_id_semester_year_fkey" FOREIGN KEY (course_id, sec_id, semester, year) REFERENCES section(course_id, sec_id, semester, year)
    TABLE "teaches" CONSTRAINT "teaches_course_id_sec_id_semester_year_fkey" FOREIGN KEY (course_id, sec_id, semester, year) REFERENCES section(course_id, sec_id, semester, year)
```

Sortie de la commande SELECT:
```bash
 course_id | sec_id | semester | year | building | room_number | time_slot_id 
-----------+--------+----------+------+----------+-------------+--------------
 BIO-101   | 1      | Summer   | 2009 | Painter  | 514         | B
 BIO-301   | 1      | Summer   | 2010 | Painter  | 514         | A
 CS-101    | 1      | Fall     | 2009 | Packard  | 101         | H
 CS-101    | 1      | Spring   | 2010 | Packard  | 101         | F
 CS-190    | 1      | Spring   | 2009 | Taylor   | 3128        | E
 CS-190    | 2      | Spring   | 2009 | Taylor   | 3128        | A
 CS-315    | 1      | Spring   | 2010 | Watson   | 120         | D
 CS-319    | 1      | Spring   | 2010 | Watson   | 100         | B
 CS-319    | 2      | Spring   | 2010 | Taylor   | 3128        | C
 CS-347    | 1      | Fall     | 2009 | Taylor   | 3128        | A
 EE-181    | 1      | Spring   | 2009 | Taylor   | 3128        | C
 FIN-201   | 1      | Spring   | 2010 | Packard  | 101         | B
 HIS-351   | 1      | Spring   | 2010 | Painter  | 514         | C
 MU-199    | 1      | Spring   | 2010 | Packard  | 101         | D
 PHY-101   | 1      | Fall     | 2009 | Watson   | 100         | A
(15 rows)
```
## 2.2
Afficher tous les renseignements sur les cours que l’on peut programmer (relation course).
```oraclesqlplus
SELECT * FROM course;
```

Sortie de la commande :
```bash
 course_id |           title            | dept_name  | credits 
-----------+----------------------------+------------+---------
 BIO-101   | Intro. to Biology          | Biology    |       4
 BIO-301   | Genetics                   | Biology    |       4
 BIO-399   | Computational Biology      | Biology    |       3
 CS-101    | Intro. to Computer Science | Comp. Sci. |       4
 CS-190    | Game Design                | Comp. Sci. |       4
 CS-315    | Robotics                   | Comp. Sci. |       3
 CS-319    | Image Processing           | Comp. Sci. |       3
 CS-347    | Database System Concepts   | Comp. Sci. |       3
 EE-181    | Intro. to Digital Systems  | Elec. Eng. |       3
 FIN-201   | Investment Banking         | Finance    |       3
 HIS-351   | World History              | History    |       3
 MU-199    | Music Video Production     | Music      |       3
 PHY-101   | Physical Principles        | Physics    |       4
(13 rows)
```

## 2.3
Afficher tous les titres des cours et les départements qui les proposent.
```oraclesqlplus
SELECT title, dept_name FROM course;
```
Sortie de la commande :
```bash
           title            | dept_name  
----------------------------+------------
 Intro. to Biology          | Biology
 Genetics                   | Biology
 Computational Biology      | Biology
 Intro. to Computer Science | Comp. Sci.
 Game Design                | Comp. Sci.
 Robotics                   | Comp. Sci.
 Image Processing           | Comp. Sci.
 Database System Concepts   | Comp. Sci.
 Intro. to Digital Systems  | Elec. Eng.
 Investment Banking         | Finance
 World History              | History
 Music Video Production     | Music
 Physical Principles        | Physics
(13 rows)
```

## 2.4
Afficher le nom des départements ainsi que leurs budgets.
```oraclesqlplus
SELECT dept_name, budget FROM department;
```

Sortie de la commande :
```bash
 dept_name  |  budget   
------------+-----------
 Biology    |  90000.00
 Comp. Sci. | 100000.00
 Elec. Eng. |  85000.00
 Finance    | 120000.00
 History    |  50000.00
 Music      |  80000.00
 Physics    |  70000.00
(7 rows)
```

## 2.5
Afficher tous les noms des enseignants et leurs départements.
```oraclesqlplus
SELECT name, dept_name FROM teacher;
```

Sortie de la commande :
```bash
    name    | dept_name  
------------+------------
 Srinivasan | Comp. Sci.
 Wu         | Finance
 Mozart     | Music
 Einstein   | Physics
 El Said    | History
 Gold       | Physics
 Katz       | Comp. Sci.
 Califieri  | History
 Singh      | Finance
 Crick      | Biology
 Brandt     | Comp. Sci.
 Kim        | Elec. Eng.
(12 rows)
```

## 2.6
Afficher tous les noms des enseignants ayant un salaire supérieur à 65000$.
```oraclesqlplus
SELECT name FROM teacher WHERE salary > 65000;
```

Sortie de la commande :
```bash
   name   
----------
 Wu
 Einstein
 Gold
 Katz
 Singh
 Crick
 Brandt
 Kim
(8 rows)
```

## 2.7
Afficher tous les noms des enseignants ayant un salaire entre 55000\$ et 85000$.
```oraclesqlplus
SELECT name FROM teacher WHERE salary BETWEEN 55000 AND 85000;
```

Sortie de la commande :
```bash
    name    
------------
 Srinivasan
 El Said
 Katz
 Califieri
 Singh
 Crick
 Kim
(7 rows)
```

## 2.8
Afficher les noms des départements, en utilisant la relation teacher et éliminer les doublons.
```oraclesqlplus
SELECT DISTINCT dept_name FROM teacher;
```

Sortie de la commande :
```bash
 dept_name  
------------
 Finance
 History
 Physics
 Music
 Comp. Sci.
 Biology
 Elec. Eng.
(7 rows)
```

## 2.9
Afficher tous les noms des enseignants du déepartement informatique ayant un salaire supérieur
strictement à 65.000$.
```oraclesqlplus
SELECT name FROM teacher WHERE dept_name = 'Comp. Sci.' AND salary > 65000;
```
Sortie de la commande :
```bash
  name  
--------
 Katz
 Brandt
(2 rows)
```

## 2.10
Afficher tous les renseignements sur les cours proposés au printemps 2010 (relation section).
```oraclesqlplus
SELECT * FROM section WHERE semester = 'Spring' AND year = 2010;
```
Sortie de la commande :
```bash
 course_id | sec_id | semester | year | building | room_number | time_slot_id 
-----------+--------+----------+------+----------+-------------+--------------
 CS-101    | 1      | Spring   | 2010 | Packard  | 101         | F
 CS-315    | 1      | Spring   | 2010 | Watson   | 120         | D
 CS-319    | 1      | Spring   | 2010 | Watson   | 100         | B
 CS-319    | 2      | Spring   | 2010 | Taylor   | 3128        | C
 FIN-201   | 1      | Spring   | 2010 | Packard  | 101         | B
 HIS-351   | 1      | Spring   | 2010 | Painter  | 514         | C
 MU-199    | 1      | Spring   | 2010 | Packard  | 101         | D
(7 rows)
```

## 2.11
Afficher tous les titres des cours dispensés par le département informatique qui ont plus de trois
crédits.
```oraclesqlplus
SELECT title FROM course WHERE dept_name = 'Comp. Sci.' AND credits > 3;
```
Sortie de la commande :
```bash
           title            
----------------------------
 Intro. to Computer Science
 Game Design
(2 rows)
```

## 2.12
Afficher tous les noms des enseignants ainsi que le nom de leur département et les noms des
bâtiments qui les hébergent.
```oraclesqlplus
SELECT teacher.name, teacher.dept_name, department.building
FROM teacher, department
WHERE teacher.dept_name = department.dept_name;
```

Sortie de la commande :
```bash
    name    | dept_name  | building 
------------+------------+----------
 Srinivasan | Comp. Sci. | Taylor
 Wu         | Finance    | Painter
 Mozart     | Music      | Packard
 Einstein   | Physics    | Watson
 El Said    | History    | Painter
 Gold       | Physics    | Watson
 Katz       | Comp. Sci. | Taylor
 Califieri  | History    | Painter
 Singh      | Finance    | Painter
 Crick      | Biology    | Watson
 Brandt     | Comp. Sci. | Taylor
 Kim        | Elec. Eng. | Taylor
(12 rows)
```

## 2.13
Afficher tous les étudiants ayant suivi au moins un cours en informatique.
```oraclesqlplus
SELECT DISTINCT student.name
FROM student, takes, course
WHERE student.id = takes.id AND takes.course_id = course.course_id AND course.dept_name = 'Comp. Sci.';
```

Sortie de la commande :
```bash
   name   
----------
 Bourikas
 Brown
 Levy
 Shankar
 Williams
 Zhang
(6 rows)
```

## 2.14
Afficher les noms des étudiants ayant suivi un cours dispensé par un enseignant nommé Eistein (éliminer les doublons).
```oraclesqlplus
SELECT DISTINCT student.name
FROM student, takes, teaches, teacher
WHERE student.id = takes.id 
  AND takes.course_id = teaches.course_id 
  AND takes.sec_id = teaches.sec_id 
  AND takes.semester = teaches.semester 
  AND takes.year = teaches.year 
  AND teaches.id = teacher.id 
  AND teacher.name = 'Eistein';
```

Sortie de la commande :
```bash
 name 
------
(0 rows)
```

## 2.15
Afficher tous les identifiants des cours et les enseignants qui les ont assurés.
```oraclesqlplus
SELECT DISTINCT name, course_id
FROM teacher, teaches
WHERE teacher.id = teaches.id;
```

Sortie de la commande :
```bash
    name    | course_id 
------------+-----------
 Srinivasan | CS-347
 Katz       | CS-101
 Crick      | BIO-301
 El Said    | HIS-351
 Srinivasan | CS-315
 Kim        | EE-181
 Einstein   | PHY-101
 Brandt     | CS-190
 Srinivasan | CS-101
 Brandt     | CS-319
 Crick      | BIO-101
 Katz       | CS-319
 Wu         | FIN-201
 Mozart     | MU-199
(14 rows)
```

## 2.16
Afficher le nombre d’inscrits pour chaque enseignement proposé au printemps 2010.
```oraclesqlplus
SELECT takes.course_id, takes.sec_id, takes.semester, takes.year, COUNT(*)
FROM takes
WHERE (semester, year) = ('Spring', 2010)
GROUP BY takes.course_id, takes.sec_id, takes.semester, takes.year;
```

Sortie de la commande :
```bash
 course_id | sec_id | semester | year | count 
-----------+--------+----------+------+-------
 CS-101    | 1      | Spring   | 2010 |     1
 CS-315    | 1      | Spring   | 2010 |     2
 CS-319    | 1      | Spring   | 2010 |     1
 CS-319    | 2      | Spring   | 2010 |     1
 FIN-201   | 1      | Spring   | 2010 |     1
 HIS-351   | 1      | Spring   | 2010 |     1
 MU-199    | 1      | Spring   | 2010 |     1
(7 rows)
```

## 2.17
Afficher les noms des départements et les salaires maximum de leurs enseignants.
```oraclesqlplus
SELECT dept_name, MAX(salary) FROM teacher
GROUP BY dept_name;
```

Sortie de la commande :
```bash
 dept_name  |   max    
------------+----------
 Finance    | 90000.00
 History    | 62000.00
 Physics    | 95000.00
 Music      | 40000.00
 Comp. Sci. | 92000.00
 Biology    | 72000.00
 Elec. Eng. | 80000.00
(7 rows)
```

## 2.18
Afficher le nombre d’inscrits pour chaque enseignement proposé.
```oraclesqlplus
SELECT takes.course_id, takes.sec_id, takes.semester, takes.year, COUNT(*)
FROM takes
GROUP BY takes.course_id, takes.sec_id, takes.semester, takes.year;
```

Sortie de la commande :
```bash
 course_id | sec_id | semester | year | count 
-----------+--------+----------+------+-------
 BIO-301   | 1      | Summer   | 2010 |     1
 CS-101    | 1      | Fall     | 2009 |     6
 CS-190    | 2      | Spring   | 2009 |     2
 CS-315    | 1      | Spring   | 2010 |     2
 BIO-101   | 1      | Summer   | 2009 |     1
 CS-101    | 1      | Spring   | 2010 |     1
 CS-319    | 2      | Spring   | 2010 |     1
 EE-181    | 1      | Spring   | 2009 |     1
 HIS-351   | 1      | Spring   | 2010 |     1
 FIN-201   | 1      | Spring   | 2010 |     1
 MU-199    | 1      | Spring   | 2010 |     1
 CS-347    | 1      | Fall     | 2009 |     2
 CS-319    | 1      | Spring   | 2010 |     1
 PHY-101   | 1      | Fall     | 2009 |     1
(14 rows)
```

## 2.19
Afficher le nombre total de cours qui ont eu lieu dans chaque bâtiment, pendant l’automne 2009 et le printemps 2010.
```oraclesqlplus
SELECT building, COUNT(*)
FROM section
WHERE (semester, year) IN ( ('Fall', 2009), ('Spring', 2010) ) GROUP BY building;`
```

Sortie de la commande :
```bash
 building | count 
----------+-------
 Packard  |     4
 Painter  |     1
 Taylor   |     2
 Watson   |     3
(4 rows)
```

## 2.20
Afficher le nombre total de cours dispensés par chaque département et qui ont eu dans le même bâtiment qui l’abrite.
```oraclesqlplus
SELECT department.dept_name, COUNT(*)
FROM section, department, teacher, teaches
WHERE (section.course_id, section.sec_id, section.semester, section.year) = (teaches.course_id, teaches.sec_id, teaches.semester, teaches.year)
  AND teacher.id = teaches.id
  AND teacher.dept_name = department.dept_name
  AND department.building = section.building
GROUP BY department.dept_name;
```

Sortie de la commande :
```bash
 dept_name  | count 
------------+-------
 Comp. Sci. |     4
 Elec. Eng. |     1
 History    |     1
 Music      |     1
 Physics    |     1
(5 rows)
````

## 2.21
Afficher les titres des cours proposés et qui ont eu lieu et les enseignants qui les ont assurés.
```oraclesqlplus
SELECT course.title, teacher.name
FROM course, section, teacher, teaches
WHERE (section.course_id, section.sec_id, section.semester, section.year) = (teaches.course_id, teaches.sec_id, teaches.semester, teaches.year)
  AND teacher.id = teaches.id
  AND course.course_id = section.course_id
GROUP BY course.title, teacher.name;
```

Sortie de la commande :
```bash
           title            |    name    
----------------------------+------------
 Image Processing           | Brandt
 World History              | El Said
 Intro. to Computer Science | Srinivasan
 Investment Banking         | Wu
 Physical Principles        | Einstein
 Database System Concepts   | Srinivasan
 Game Design                | Brandt
 Robotics                   | Srinivasan
 Genetics                   | Crick
 Intro. to Digital Systems  | Kim
 Intro. to Computer Science | Katz
 Music Video Production     | Mozart
 Image Processing           | Katz
 Intro. to Biology          | Crick
(14 rows)
```

## 2.22
Afficher le nombre total de cours qui ont eu lieu pour chacune des périodes Summer, Fall et Spring.
```oraclesqlplus
SELECT section.semester, COUNT(*)
FROM section
GROUP BY section.semester;
```

Sortie de la commande :
```bash
 semester | count 
----------+-------
 Summer   |     2
 Spring   |    10
 Fall     |     3
(3 rows)
```

## 2.23
Afficher pour chaque étudiant le nombre total de crédits qu’il a obtenu, en suivant des cours qui n’ont pas été proposé par son département.
```oraclesqlplus
SELECT student.id, SUM(credits)
FROM student, takes, course
WHERE student.id = takes.id
    AND takes.course_id = course.course_id
    AND student.dept_name != course.dept_name
GROUP BY student.id;
```

Sortie de la commande :
```bash
  id   | sum 
-------+-----
 45678 |  11
 98765 |   7
(2 rows)
```

## 2.24
Pour chaque département, afficher le nombre total de crédits des cours qui ont eu lieu dans ce département.
```oraclesqlplus
SELECT section.building, SUM(course.credits)
FROM section, course
WHERE section.course_id = course.course_id
GROUP BY section.building;
```

Sortie de la commande :
```bash
 building | sum 
----------+-----
 Taylor   |  17
 Packard  |  14
 Painter  |  11
 Watson   |  10
(4 rows)
```
