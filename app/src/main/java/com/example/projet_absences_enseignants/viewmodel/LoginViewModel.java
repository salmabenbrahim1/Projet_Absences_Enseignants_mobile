package com.example.projet_absences_enseignants.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginViewModel extends ViewModel {

    private FirebaseAuth mAuth;
    private MutableLiveData<Boolean> isLoginSuccessful = new MutableLiveData<>();
    private MutableLiveData<String> userRole = new MutableLiveData<>();

    public LoginViewModel() {
        mAuth = FirebaseAuth.getInstance();
    }

    public LiveData<Boolean> getIsLoginSuccessful() {
        return isLoginSuccessful;
    }

    public LiveData<String> getUserRole() {
        return userRole;
    }

    public void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            fetchUserRole(user.getUid()); // Récupère le rôle de l'utilisateur
                        } else {
                            isLoginSuccessful.setValue(false);
                        }
                    } else {
                        isLoginSuccessful.setValue(false);
                    }
                });
    }

    private void fetchUserRole(String userId) {
        // Remplacez par votre collection Firestore
        FirebaseFirestore.getInstance().collection("users").document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String role = documentSnapshot.getString("role");
                        userRole.setValue(role);
                        isLoginSuccessful.setValue(true);
                    } else {
                        isLoginSuccessful.setValue(false);
                    }
                })
                .addOnFailureListener(e -> {
                    isLoginSuccessful.setValue(false);
                });
    }
}
