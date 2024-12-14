package com.example.projet_absences_enseignants.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.projet_absences_enseignants.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfilFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private TextView tvName, tvEmail, tvRole;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profil, container, false);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        tvName = view.findViewById(R.id.tvName);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvRole = view.findViewById(R.id.tvRole);

        getUserInfo();

        return view;
    }

    private void getUserInfo() {
        String userId = mAuth.getCurrentUser().getUid(); // Obtenez l'ID de l'utilisateur connecté
        DocumentReference docRef = db.collection("users").document(userId);

        docRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                String name = documentSnapshot.getString("name");
                String email = documentSnapshot.getString("email");
                String role = documentSnapshot.getString("role");

                tvName.setText(name);
                tvEmail.setText("Email:"+email);
                tvRole.setText(role);
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(getContext(), "Erreur lors de la connexion ", Toast.LENGTH_SHORT).show();
        });
    }
}
