package com.example.projet_absences_enseignants.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.projet_absences_enseignants.model.Reclamation;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ReclamationViewModel extends ViewModel {

    private MutableLiveData<List<Reclamation>> reclamationList;
    private FirebaseFirestore db;

    public ReclamationViewModel() {
        db = FirebaseFirestore.getInstance();
        reclamationList = new MutableLiveData<>(new ArrayList<>());
        getReclamationsFromFirebase();
    }

    // Retourner la liste des réclamations
    public LiveData<List<Reclamation>> getReclamations() {
        return reclamationList;
    }

    // Charger les réclamations depuis Firestore
    private void getReclamationsFromFirebase() {
        db.collection("reclamations").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Reclamation> reclamations = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Reclamation reclamation = document.toObject(Reclamation.class);
                            reclamation.setReclamationID(document.getId());
                            reclamations.add(reclamation);
                            Log.d("Firestore", "Réclamation récupérée : " + reclamation.getReclamationID());
                        }
                        reclamationList.setValue(reclamations);
                    } else {
                        Log.e("Firestore", "Erreur : ", task.getException());
                    }
                });
    }

    // Supprimer une réclamation de Firestore
    public void deleteReclamation(Reclamation reclamation) {
        db.collection("reclamations").document(reclamation.getReclamationID())
                .delete()
                .addOnSuccessListener(aVoid -> {
                    List<Reclamation> currentList = reclamationList.getValue();
                    if (currentList != null) {
                        currentList.remove(reclamation);
                        reclamationList.setValue(currentList); // Met à jour la liste
                    }
                })
                .addOnFailureListener(e -> Log.e("Firestore", "Erreur de suppression", e));
    }
}
