package com.example.spring1;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
public class MyApi
{
    public static ArrayList<Etudiant> liste = new ArrayList<>();
    static
    {
        liste.add(new Etudiant(0, "A", 19));
        liste.add(new Etudiant(1, "B", 20));
        liste.add(new Etudiant(2, "C", 13));
        liste.add(new Etudiant(3, "D", 16));
    }

    @GetMapping(value="/liste")
    public Collection<Etudiant> getAllEtudiants()
    {
        return liste;
    }

    @GetMapping(value="/getEtudiant")
    public Etudiant getEtudiant(int identifiant)
    {
        return liste.get(identifiant);
    }

    @PostMapping(value="/addEtudiant")
    public Etudiant addEtudiant(Etudiant etudiant)
    {
        liste.add(etudiant);
        return etudiant;
    }

    @DeleteMapping(value="/delete")
    public void deleteEtudiant(int identifiant)
    {
        liste.remove(identifiant);
    }

    @PutMapping(value="/modifier")
    public void editEtudiant(int identifiant, String nom)
    {
        liste.get(identifiant).setNom(nom);
    }

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

    @GetMapping(value="/etudiant")
    public Etudiant getEtudiant()
    {
        return new Etudiant(1, "A", 19);
    }

    @GetMapping(value="/somme")
    public double somme(double a, double b)
    {
        return a + b;
    }
}
