package com.example.projet_absences_enseignants.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.projet_absences_enseignants.model.Absence;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class GestionAbsenceViewModel extends ViewModel {

    private final MutableLiveData<List<Absence>> absences = new MutableLiveData<>();
    private List<Absence> absenceList = new ArrayList<>();

    public LiveData<List<Absence>> getAbsences() {
        return absences;
    }

    public List<Absence> getAbsenceList() {
        return absenceList;
    }

    // Méthode pour récupérer les absences depuis Firestore
    public void getAbsencesFromFirebase() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("absences").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        absenceList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Absence absence = document.toObject(Absence.class);
                            absence.setAbsenceID(document.getId());
                            absenceList.add(absence);
                            Log.d("Firestore", "Absence: " + absence.getDate());
                        }
                        absences.setValue(absenceList);
                    } else {
                        Log.e("Firestore", "Erreur lors de la récupération des données : ", task.getException());
                    }
                });
    }

    // Méthode pour supprimer une absence de Firestore
    public void deleteAbsence(Absence absence, Context context) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("absences").document(absence.getAbsenceID())
                .delete()
                .addOnSuccessListener(aVoid -> {
                    Log.d("Firestore", "Absence supprimée avec succès");
                    if (context != null) {
                        Toast.makeText(context, "Absence supprimée", Toast.LENGTH_SHORT).show();
                    }
                    absenceList.remove(absence);
                    absences.setValue(absenceList);
                })
                .addOnFailureListener(e -> {
                    Log.e("Firestore", "Erreur lors de la suppression de l'absence : ", e);
                    if (context != null) {
                        Toast.makeText(context, "Erreur lors de la suppression", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public void updateAbsence(Absence absence) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("absences")
                .document(absence.getAbsenceID())
                .set(absence) // Remplace toutes les données de l'absence
                .addOnSuccessListener(aVoid -> {
                    Log.d("Firestore", "Absence mise à jour avec succès");
                    // Mettre à jour la liste locale
                    int index = absenceList.indexOf(absence);
                    if (index != -1) {
                        absenceList.set(index, absence); // Remplacer l'absence mise à jour dans la liste
                        absences.setValue(absenceList);
                    }
                })
                .addOnFailureListener(e -> Log.e("Firestore", "Erreur lors de la mise à jour de l'absence : ", e));
    }

}