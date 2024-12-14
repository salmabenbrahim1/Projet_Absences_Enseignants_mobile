package com.example.projet_absences_enseignants.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.example.projet_absences_enseignants.model.Absence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddAbsenceViewModel extends ViewModel {

    private MutableLiveData<String> toastMessage = new MutableLiveData<>();
    private MutableLiveData<List<Absence>> absenceList = new MutableLiveData<>(); // Liste pour les absences récupérées
    private FirebaseFirestore db = FirebaseFirestore.getInstance(); // Initialisation de Firestore

    // Getter pour observer les messages Toast
    public LiveData<String> getToastMessageLiveData() {
        return toastMessage;
    }

    // Getter pour observer la liste des absences
    public LiveData<List<Absence>> getAbsenceList() {
        return absenceList;
    }

    // Méthode pour enregistrer une absence dans Firestore
    public void saveAbsence(String date, String heure, String classe, String justification, String enseignantId, String agentId) {
        // Création d'un objet pour représenter l'absence
        Map<String, Object> absenceData = new HashMap<>();
        absenceData.put("date", date);
        absenceData.put("heure", heure);
        absenceData.put("classe", classe);
        absenceData.put("justification", justification);
        absenceData.put("enseignantId", enseignantId);
        absenceData.put("agentId", agentId);

        // Ajout de l'absence dans la collection "absences"
        db.collection("absences")
                .add(absenceData)
                .addOnSuccessListener(documentReference -> {
                    toastMessage.setValue("Absence enregistrée avec succès !");
                })
                .addOnFailureListener(e -> {
                    toastMessage.setValue("Erreur lors de l'enregistrement : " + e.getMessage());
                });
    }

    // Méthode pour récupérer les absences depuis Firestore
    public void fetchAbsences() {
        db.collection("absences")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Absence> absences = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        // Récupérer les données de chaque document et les convertir en objets Absence
                        Absence absence = document.toObject(Absence.class);
                        absences.add(absence);
                    }
                    absenceList.setValue(absences); // Mettre à jour la liste d'absences
                })
                .addOnFailureListener(e -> {
                    toastMessage.setValue("Erreur lors de la récupération des absences : " + e.getMessage());
                });
    }
}
