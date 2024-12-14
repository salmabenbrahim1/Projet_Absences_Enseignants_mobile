package com.example.projet_absences_enseignants.model;

public class Reclamation {
    private String date;
    private String heure;
    private String justification;
    private String reclamationID;

    public Reclamation() {
    }

    public Reclamation(String date, String heure, String justification) {
        this.date = date;
        this.heure = heure;
        this.justification = justification;
    }

    // Getters et Setters
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

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    public String getReclamationID() {
        return reclamationID;
    }

    public void setReclamationID(String reclamationID) {
        this.reclamationID = reclamationID;
    }
}
