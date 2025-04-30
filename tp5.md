# Exercice 1
Sur chacune des sessions il faut executer la commande suivante :
```oraclesqlplus
SET AUTOCOMMIT OFF;
```

Pour PostgreSQL c'est:
```postgresql
\set AUTOCOMMIT off
```

## 1.1
Voici la sortie terminale lors de la creation de la table transaction sur la session 1 :
```postgresql
univ=# CREATE TABLE transaction(
idTransaction varchar(44),
valTransaction numeric(10));
CREATE TABLE
```

## 2.2
Sortie terminale après l'insertion de la première ligne dans la table transaction sur la session 2 :
```postgresql
univ=# INSERT INTO transaction VALUES ('1', 0);
ERROR:  relation "transaction" does not exist
LINE 1: INSERT INTO transaction VALUES ('1', 0);
                    ^
univ=!# ROLLBACK;
ROLLBACK
```
On doit faire un COMMIT sur la session 1 avant de faire un INSERT sur la session 2.

Après avoir fait le COMMIT sur la session 1, voici la sortie terminale de la session 2 :
```postgresql
univ=*# INSERT INTO transaction VALUES ('1', 0);
INSERT 0 1
univ=*# COMMIT;
COMMIT
univ=# SELECT * FROM transaction;
 idtransaction | valtransaction 
---------------+----------------
 1             |              0
(1 row)
```

## 2.3
Apres la commande 'quit;' sur la session 2, voici l'affichage de la table transaction sur la session 1 :
```postgresql
**univ=# SELECT * FROM transaction;
 idtransaction | valtransaction 
---------------+----------------
 1             |              0
(1 row)
```
La deuxième insertion n'est pas visible sur la session 1 car elle n'a pas été commitée.

## 2.4
On execute les commandes suivantes sur la session 1 :
```postgresql
INSERT INTO transaction VALUES ('2', 1);
INSERT INTO transaction VALUES ('3', 2);
INSERT INTO transaction VALUES ('4', 3);
INSERT INTO transaction VALUES ('5', 4);
```
On quitte brutalement la session 1 avec Ctrl+C.
On se reconnecte à la session 1 et on execute la commande suivante :
```postgresql
SELECT * FROM transaction;
```
Voici la sortie terminale :
```postgresql
 idtransaction | valtransaction 
---------------+----------------
 1             |              0
(1 row)
```
La table transaction n'a pas été modifiée, car les insertions n'ont pas été commités avant la fermeture de la session 1.

## 2.5
Insertions de la session 2 :
```postgresql
INSERT INTO transaction VALUES ('2', 1);
INSERT INTO transaction VALUES ('3', 2);
INSERT INTO transaction VALUES ('4', 3);
INSERT INTO transaction VALUES ('5', 4);
```
Modification de la table transaction :
```postgresql
univ=# ALTER TABLE transaction ADD COLUMN val2transaction numeric(10);
```

Voici la sortie terminale apres avec la commande ROLLBACK :
```postgresql
univ=# INSERT INTO transaction VALUES ('2', 1);
INSERT INTO transaction VALUES ('3', 2);
INSERT INTO transaction VALUES ('4', 3);
INSERT INTO transaction VALUES ('5', 4);
INSERT 0 1
INSERT 0 1
INSERT 0 1
INSERT 0 1
univ=*# ALTER TABLE transaction ADD COLUMN val2transaction numeric(10);
ALTER TABLE
univ=*# ROLLBACK;
ROLLBACK
```

Si on execute la commande suivante :
```postgresql
SELECT * FROM transaction;
```
Voici la sortie terminale :
```postgresql
 idtransaction | valtransaction 
---------------+----------------
 1             |              0
(1 row)
```
La table transaction n'a pas été modifiée, car les insertions n'ont pas été commités et un rollback a été effectué.

## 2.6
Une session est une connexion à la base de données, elle peut être ouverte par un utilisateur pour exécuter des requêtes SQL.
Une transaction est un ensemble d'opérations qui sont exécutées comme une seule unité de travail.
Une transaction peut être validée (commit) pour rendre les modifications permanentes dans la base de données, ou annulée (rollback) pour annuler toutes les modifications effectuées depuis le
dernier commit.
