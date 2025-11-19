package com.example.applicationgestionrh;

import java.io.Serializable;

public class Employe implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String nom;
    private String prenom;
    private String matricule;
    private String departement;
    private String grade;
    private String statut;

    public Employe(int id, String nom, String prenom, String matricule, String departement, String grade, String statut) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.matricule = matricule;
        this.departement = departement;
        this.grade = grade;
        this.statut = statut;
    }

    public Employe(String matricule, String nom, String prenom, String grade, String departement, String statut) {
        this.id = -1;
        this.matricule = matricule;
        this.nom = nom;
        this.prenom = prenom;
        this.grade = grade;
        this.departement = departement;
        this.statut = statut;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getMatricule() { return matricule; }
    public String getDepartement() { return departement; }
    public String getGrade() { return grade; }
    public String getStatut() { return statut; }
    public String getNomComplet() {
        return (prenom + " " + nom).toLowerCase();
    }
}