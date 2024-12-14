package com.example.projet_absences_enseignants.model;

import java.io.Serializable;

public class Absence implements Serializable {
    private String date;
    private String heure;
    private String classe;
    private String enseignement;
    private String statut;
    private String teacherID;  // Identifiant de l'enseignant
    private String absenceID;  // Identifiant unique de l'absence

    public Absence() {
        // Constructeur vide requis pour la désérialisation Firebase
    }

    // Constructeur complet
    public Absence(String date, String heure, String classe, String enseignement, String statut, String teacherID, String absenceID) {
        this.date = date;
        this.heure = heure;
        this.classe = classe;
        this.enseignement = enseignement;
        this.statut = statut;
        this.teacherID = teacherID;
        this.absenceID = absenceID;
    }

    // Constructeur sans absenceID (Firestore génère cet ID automatiquement)
    public Absence(String date, String heure, String classe, String enseignement, String statut, String teacherID) {
        this.date = date;
        this.heure = heure;
        this.classe = classe;
        this.enseignement = enseignement;
        this.statut = statut;
        this.teacherID = teacherID;
    }

    // Getters et setters
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getEnseignement() {
        return enseignement;
    }

    public void setEnseignement(String enseignement) {
        this.enseignement = enseignement;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }

    public String getAbsenceID() {
        return absenceID;
    }

    public void setAbsenceID(String absenceID) {
        this.absenceID = absenceID;
    }
}
