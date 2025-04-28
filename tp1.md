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

## 2.2
Afficher tous les renseignements sur les cours que l’on peut programmer (relation course).
```oraclesqlplus
SELECT * FROM course;
```

## 2.3
Afficher tous les titres des cours et les départements qui les proposent.
```oraclesqlplus
SELECT title, dept_name FROM course;
```

# 2.4
Afficher le nom des départements ainsi que leurs budgets.
```oraclesqlplus
SELECT dept_name, budget FROM department;
```

## 2.5
Afficher tous les noms des enseignants et leurs département.
```oraclesqlplus
SELECT name, dept_name FROM teacher;
```

## 2.6
Afficher tous les noms des enseignants ayant un salaire supérieur à 65000$.
```oraclesqlplus
SELECT name FROM teacher WHERE salary > 65000;
```

## 2.7
Afficher tous les noms des enseignants ayant un salaire entre 55000\$ et 85000$.
```oraclesqlplus
SELECT name FROM teacher WHERE salary BETWEEN 55000 AND 85000;
```

## 2.8
Afficher les noms des départements, en utilisant la relation teacher et éliminer les doublons.
```oraclesqlplus
SELECT DISTINCT dept_name FROM teacher;
```
## 2.9
Afficher tous les noms des enseignants du déepartement informatique ayant un salaire supérieur
strictement à 65.000$.
```oraclesqlplus
SELECT name FROM teacher WHERE dept_name = 'Comp. Sci.' AND salary > 65000;
```

## 2.10
Afficher tous les renseignements sur les cours proposés au printemps 2010 (relation section).
```oraclesqlplus
SELECT * FROM section WHERE semester = 'Spring' AND year = 2010;
```

## 2.11
Afficher tous les titres des cours dispensés par le département informatique qui ont plus de trois
crédits.
```oraclesqlplus
SELECT title FROM course WHERE dept_name = 'Comp. Sci.' AND credit > 3;
```

## 2.12
Afficher tous les noms des enseignants ainsi que le nom de leur département et les noms des
bâtiments qui les hébergent.
```oraclesqlplus
SELECT teacher.name, teacher.dept_name, departement.building
FROM teacher, department
WHERE teacher.dept_name = department.dept_name;
```

## 2.13
Afficher tous les étudiants ayant suivi au moins un cours en informatique.
```oraclesqlplus
SELECT DISTINCT student.name
FROM student, takes, course
WHERE student.id = takes.id AND takes.course_id = course.course_id AND course.dept_name = 'Comp. Sci.';
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

## 2.15
Afficher tous les identifiants des cours et les enseignants qui les ont assurés.
```oraclesqlplus
SELECT name, course_id
FROM teacher, teaches
WHERE teacher.id = teaches.id;
```

## 2.16
Afficher le nombre d’inscrits pour chaque enseignement proposé au printemps 2010.
```oraclesqlplus
SELECT building, COUNT(*)
FROM section 
WHERE (semester, year) IN ( (Fall, 2010), (Spring, 2010) ) GROUP BY building;
```
## 2.17
Afficher les noms des départements et les salaires maximum de leurs enseignants.
```oraclesqlplus
SELECT dept_name, MAX(salary) FROM teacher
GROUP BY dept_name;
```

## 2.18
Afficher le nombre d’inscrits pour chaque enseignement proposé.
```oraclesqlplus
SELECT takes.course_id, takes.sec_id, takes.semester, takes.year, COUNT(*)
FROM takes
GROUP BY takes.course_id, takes.sec_id, takes.semester, takes.year;
```

## 2.19
Afficher le nombre total de cours qui ont eu lieu dans chaque bâtiment, pendant l’automne 2009 et le printemps 2010.
```oraclesqlplus
SELECT building, COUNT(*)
FROM section
WHERE (semester, year) IN ( (Fall, 2009), (Spring, 2010) ) GROUP BY building;
```

## 2.20
Afficher le nombre total de cours dispensés par chaque département et qui ont eu dans le même bâtiment qui l’abrite.
```oraclesqlplus
SELECT departement.dept_name, COUNT(*)
FROM section, department, teacher, teaches
WHERE (section.course_id, section.sec_id, section.semester, section.year) = (teaches.course_id, teaches.sec_id, teaches.semester, teaches.year)
  AND teacher.id = teaches.id
  AND teacher.dept_name = department.dept_name
  AND departement.building = section.building
GROUP BY departement.dept_name;
```

## 2.21
Afficher les titres des cours proposés et qui ont eu lieu et les enseignants qui les ont assurés.
```oraclesqlplus
SELECT course.title, teacher.name
FROM course, section, teacher, teaches
WHERE (section.course_id, section.sec_id, section.semester, section.year) = (teaches.course_id, teaches.sec_id, teaches.semester, teaches.year)
  AND teacher.id = teaches.id
  AND course.course_id = section.course_id
GROUP BY course.title;
```

## 2.22
Afficher le nombre total de cours qui ont eu lieu pour chacune des périodes Summer, Fall et Spring.
```oraclesqlplus
SELECT section.semester, COUNT(*)
FROM section
GROUP BY section.semester;
```

## 2.23
Afficher pour chaque étudiant le nombre total de crédits qu’il a obtenu, en suivant des cours qui n’ont pas été proposé par son département.
```oraclesqlplus
SELECT student.id, SUM(credit)
FROM student, takes, course
WHERE student.id = takes.id
    AND takes.course_id = course.course_id
    AND student.dept_name != course.dept_name
GROUP BY student.id;
```

## 2.24
Pour chaque département, afficher le nombre total de crédits des cours qui ont eu lieu dans ce département.
```oraclesqlplus
SELECT section.building, SUM(course.credit)
FROM section, course
WHERE section.course_id = course.course_id
GROUP BY section.building;
```
