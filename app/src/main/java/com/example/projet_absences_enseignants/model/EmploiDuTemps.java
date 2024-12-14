package com.example.projet_absences_enseignants.model;

import java.util.HashMap;
import java.util.Map;

public class EmploiDuTemps {
    private String id;  // ID Firestore
    private String jour;
    private String matiere;
    private String heureDebut;
    private String heureFin;

    public EmploiDuTemps() { }

    public EmploiDuTemps(String jour, String matiere, String heureDebut, String heureFin) {
        this.jour = jour;
        this.matiere = matiere;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getJour() { return jour; }
    public void setJour(String jour) { this.jour = jour; }

    public String getMatiere() { return matiere; }
    public void setMatiere(String matiere) { this.matiere = matiere; }

    public String getHeureDebut() { return heureDebut; }
    public void setHeureDebut(String heureDebut) { this.heureDebut = heureDebut; }

    public String getHeureFin() { return heureFin; }
    public void setHeureFin(String heureFin) { this.heureFin = heureFin; }

    // Méthode toMap pour convertir l'objet en un Map<String, Object> pour Firestore
    public Map<String, Object> toMap() {
        Map<String, Object> emploiDuTempsMap = new HashMap<>();
        emploiDuTempsMap.put("jour", this.jour);
        emploiDuTempsMap.put("matiere", this.matiere);
        emploiDuTempsMap.put("heureDebut", this.heureDebut);
        emploiDuTempsMap.put("heureFin", this.heureFin);
        return emploiDuTempsMap;
    }
}
