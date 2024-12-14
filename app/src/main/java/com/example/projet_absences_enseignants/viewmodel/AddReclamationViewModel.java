package com.example.projet_absences_enseignants.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.projet_absences_enseignants.model.Reclamation;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddReclamationViewModel extends AndroidViewModel {
    private FirebaseFirestore firestore;
    private MutableLiveData<Boolean> isReclamationAdded;

    public AddReclamationViewModel(Application application) {
        super(application);
        firestore = FirebaseFirestore.getInstance();
        isReclamationAdded = new MutableLiveData<>();
    }

    public MutableLiveData<Boolean> getIsReclamationAdded() {
        return isReclamationAdded;
    }

    public void addReclamation(String date, String heure, String justification) {
        Reclamation reclamation = new Reclamation(date, heure, justification);

        firestore.collection("reclamations")
                .add(reclamation)
                .addOnSuccessListener(documentReference -> isReclamationAdded.setValue(true))
                .addOnFailureListener(e -> isReclamationAdded.setValue(false));
    }
}
