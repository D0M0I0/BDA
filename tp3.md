# Etape 1
Créer et configurer un projet SpringBoot sur IntelliJ IDEA.
![](Images/1.png "Etape 1.1")
![](Images/2.png "Etape 1.2")

# Etape 2
On créer une nouvelle classe qui nous servira d'API (un controller).
Ici notre classe se nomme 'MyApi' et est située dans 'src/main/java/com/example/spring1/'
Voici le code de la classe qui nous permet de tester notre API :
```java
@RestController
public class MyApi
{
    @GetMapping(value="/bonjour")
    public String bonjour()
    {
        return "Bonjour";
    }

    @GetMapping(value="/bonsoir")
    public String bonsoir()
    {
        return "Bonsoir";
    }
}
```
Maintenant lorsqu'on se connecte via le navigateur à l'adresse suivante http://localhost:8080/bonjour on voit le message "Bonjour" s'afficher.
![](Images/3.png "Affichage de Bonjour")
De même pour l'adresse http://localhost:8080/bonsoir qui affiche "Bonsoir".
![](Images/4.png "Affichage de Bonsoir")

Dans le fichier 'src/main/resources/application.properties' on peut configurer notre application. Par exemple, on peut changer le port par défaut (8080) en ajoutant la ligne suivante :
```properties
server.port=9999
```
Maintenant le serveur écoute sur le port 9999. On peut le vérifier en se rendant à l'adresse http://localhost:9999/bonjour qui affiche toujours "Bonjour".

# Etape 3
On crée une nouvelle classe 'Etudiant' qui nous servira de modèle pour notre API.
```java
public class Etudiant
{
    private int identifiant;
    private String nom;
    private double moyenne;

    public Etudiant()
    {
    }

    public Etudiant(int identifiant, String nom, double moyenne)
    {
        this.identifiant = identifiant;
        this.nom = nom;
        this.moyenne = moyenne;
    }

    public int getIdentifiant()
    {
        return identifiant;
    }

    public void setIdentifiant(int identifiant)
    {
        this.identifiant = identifiant;
    }

    public String getNom()
    {
        return nom;
    }

    public void setNom(String nom)
    {
        this.nom = nom;
    }

    public double getMoyenne()
    {
        return moyenne;
    }

    public void setMoyenne(double moyenne)
    {
        this.moyenne = moyenne;
    }
}
```
Et on peut l'ajouter a notre API 'MyApi' pour créer une nouvelle méthode qui renvoie un objet de type 'Etudiant'.
```java
@GetMapping(value="/etudiant")
public Etudiant getEtudiant()
{
    return new Etudiant(1, "A", 19);
}
```
Maintenant si on se rend à l'adresse http://localhost:9999/etudiant on voit s'afficher un objet de type 'Etudiant' au format JSON.
![](Images/5.png "Affichage de l'objet Etudiant")

# Etape 4
On va maintenant faire une fonction avec paramètres.
```java
@GetMapping(value="/somme")
public double somme(double a, double b)
{
    return a + b;
}
```
Maintenant si on se rend à l'adresse http://localhost:9999/somme?a=1&b=2 on voit s'afficher le résultat de la somme de a et b.
![](Images/6.png "Affichage de la somme")

# Etape 5
On va simuler une base de données en créant une liste d'étudiants.
```java
public static Collection<Etudiant> liste = new ArrayList<>();
static
{
    liste.add(new Etudiant(0, "A", 19));
    liste.add(new Etudiant(1, "B", 20));
    liste.add(new Etudiant(2, "C", 13));
    liste.add(new Etudiant(3, "D", 16));
}
```
On va maintenant créer une méthode qui renvoie la liste d'étudiants.
```java
@GetMapping(value="/liste")
public Collection<Etudiant> getAllEtudiants()
{
    return liste;
}
```

Maintenant si on se rend à l'adresse http://localhost:9999/liste, on voit s'afficher la liste d'étudiants au format JSON.
![](Images/7.png "Affichage de la liste d'étudiants")

# Etape 6
On peut créer une méthode qui renvoie un étudiant en fonction de son identifiant.
```java
@GetMapping(value="/getEtudiant")
public Etudiant getEtudiant(int identifiant)
{
    return liste.get(identifiant);
}
```
Maintenant si on se rend à l'adresse http://localhost:9999/getEtudiant?identifiant=0, on voit s'afficher l'étudiant avec l'identifiant 0.
![](Images/8.png "Affichage de l'étudiant avec l'identifiant 0")
