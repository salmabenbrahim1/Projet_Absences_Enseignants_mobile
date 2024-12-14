package com.example.projet_absences_enseignants.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserRepository {
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    public UserRepository() {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    public void createUser(String name, String email, String password, String role, OnUserCreatedListener listener) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String userId = mAuth.getCurrentUser().getUid();
                        User user = new User(name, email, role);
                        db.collection("users").document(userId)
                                .set(user)
                                .addOnSuccessListener(aVoid -> listener.onSuccess())
                                .addOnFailureListener(e -> listener.onFailure(e.getMessage()));
                    } else {
                        listener.onFailure(task.getException().getMessage());
                    }
                });
    }

    public interface OnUserCreatedListener {
        void onSuccess();
        void onFailure(String error);
    }
}
