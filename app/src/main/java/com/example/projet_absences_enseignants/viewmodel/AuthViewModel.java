package com.example.projet_absences_enseignants.viewmodel;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.projet_absences_enseignants.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class AuthViewModel extends AndroidViewModel {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private MutableLiveData<Boolean> isLoggedOut = new MutableLiveData<>(false);  // Ajout de la notification de déconnexion

    private MutableLiveData<Boolean> isUserLoggedIn = new MutableLiveData<>();
    private MutableLiveData<User> currentUser = new MutableLiveData<>();
    private MutableLiveData<String> userRole = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private MutableLiveData<String> errorMessage = new MutableLiveData<>(); // Nouveau champ pour les messages d'erreur

    public AuthViewModel(@NonNull Application application) {
        super(application);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        checkUserLoginStatus();
    }

    private void checkUserLoginStatus() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            isUserLoggedIn.setValue(true);
            fetchUserRole(user.getUid());
        } else {
            isUserLoggedIn.setValue(false);
        }
    }

    public void AddUserFragment(String email, String password, String role) {
        isLoading.setValue(true);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    isLoading.setValue(false);
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            User newUser = new User(user.getUid(), email, role);
                            db.collection("users").document(user.getUid()).set(newUser)
                                    .addOnSuccessListener(aVoid -> {
                                        currentUser.setValue(newUser);
                                        userRole.setValue(role);
                                    })
                                    .addOnFailureListener(e -> {
                                        errorMessage.setValue("Erreur lors de l'enregistrement de l'utilisateur : " + e.getMessage());
                                        Log.e("AuthViewModel", "Erreur d'enregistrement :", e);
                                    });
                        }
                    } else {
                        errorMessage.setValue("Erreur d'inscription : " + task.getException().getMessage());
                        Log.e("AuthViewModel", "Erreur d'inscription :", task.getException());
                    }
                });
    }

    public void login(String email, String password) {
        isLoading.setValue(true);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    isLoading.setValue(false);
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            fetchUserRole(user.getUid());
                        }
                    } else {
                        errorMessage.setValue("Erreur de connexion : " + task.getException().getMessage());
                        Log.e("AuthViewModel", "Erreur de connexion :", task.getException());
                    }
                });
    }

    private void fetchUserRole(String userId) {
        db.collection("users").document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String role = documentSnapshot.getString("role");
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            User loggedInUser = new User(user.getUid(), user.getEmail(), role);
                            currentUser.setValue(loggedInUser);
                            userRole.setValue(role);
                        }
                    } else {
                        userRole.setValue(null);
                        errorMessage.setValue("Le rôle de l'utilisateur est introuvable.");
                    }
                })
                .addOnFailureListener(e -> {
                    errorMessage.setValue("Erreur de récupération du rôle : " + e.getMessage());
                    Log.e("AuthViewModel", "Erreur de récupération du rôle :", e);
                });
    }

    public void logout() {
        mAuth.signOut();
        isLoggedOut.setValue(true);  // Notifie qu'un utilisateur est déconnecté
    }


    public LiveData<Boolean> getIsUserLoggedIn() {
        return isUserLoggedIn;
    }
    public LiveData<Boolean> getIsLoggedOut() {
        return isLoggedOut;
    }

    public LiveData<User> getCurrentUser() {
        return currentUser;
    }

    public LiveData<String> getUserRole() {
        return userRole;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;  // Retourne l'erreur
    }
}
