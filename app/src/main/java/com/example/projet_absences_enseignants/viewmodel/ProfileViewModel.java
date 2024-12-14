package com.example.projet_absences_enseignants.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

public class ProfileViewModel extends ViewModel {
    private MutableLiveData<User> userLiveData = new MutableLiveData<>();
    private FirebaseFirestore db;

    public ProfileViewModel() {
        db = FirebaseFirestore.getInstance();
    }

    public LiveData<User> getUser() {
        return userLiveData;
    }

    public void loadUserInfo() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser().getUid();
        db.collection("users").document(userId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        User user = documentSnapshot.toObject(User.class);
                        userLiveData.setValue(user);
                    }
                });
    }
}
