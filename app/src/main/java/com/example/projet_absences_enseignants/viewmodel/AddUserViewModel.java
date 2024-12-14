package com.example.projet_absences_enseignants.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.projet_absences_enseignants.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddUserViewModel extends AndroidViewModel {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private MutableLiveData<String> successMessage;
    private MutableLiveData<String> errorMessage;

    public AddUserViewModel(Application application) {
        super(application);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        successMessage = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
    }

    public MutableLiveData<String> getSuccessMessage() {
        return successMessage;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void addUser(String name, String email, String password, String role) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String userId = mAuth.getCurrentUser().getUid();
                        User user = new User(name, email, role);
                        db.collection("users").document(userId)
                                .set(user)
                                .addOnSuccessListener(aVoid -> {
                                    successMessage.setValue("Utilisateur ajouté avec succès");
                                })
                                .addOnFailureListener(e -> {
                                    errorMessage.setValue("Erreur lors de l'ajout de l'utilisateur.");
                                });
                    } else {
                        String errorMsg = task.getException() != null ? task.getException().getMessage() : "Échec de l'ajout de l'utilisateur";
                        errorMessage.setValue(errorMsg);
                    }
                });
    }
}
