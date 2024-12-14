package com.example.projet_absences_enseignants.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.projet_absences_enseignants.R;
import com.example.projet_absences_enseignants.viewmodel.AddAbsenceViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class AddAbsenceFragment extends Fragment {

    private AddAbsenceViewModel addAbsenceViewModel;
    private Spinner spinnerJustification, spinnerEnseignant;
    private EditText etDate, etHeure, etClasse;
    private Button btnSaveAbsence;
    private List<String> enseignantIds;
    private List<String> enseignantNames;
    private String agentId; // ID de l'agent connecté

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialise le ViewModel pour l'ajout des absences
        addAbsenceViewModel = new ViewModelProvider(this).get(AddAbsenceViewModel.class);

        View root = inflater.inflate(R.layout.fragment_add_absence, container, false);

        etDate = root.findViewById(R.id.etDate);
        etHeure = root.findViewById(R.id.etHeure);
        etClasse = root.findViewById(R.id.etClasse);
        spinnerJustification = root.findViewById(R.id.spinnerJustification);
        spinnerEnseignant = root.findViewById(R.id.spinnerEnseignement);
        btnSaveAbsence = root.findViewById(R.id.btnSaveAbsence);

        ArrayAdapter<CharSequence> adapterJustification = ArrayAdapter.createFromResource(
                getContext(),
                R.array.justification_array,
                android.R.layout.simple_spinner_item
        );
        adapterJustification.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerJustification.setAdapter(adapterJustification);

        // Initialisation des listes pour les enseignants
        enseignantIds = new ArrayList<>();
        enseignantNames = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Récupérer les enseignants depuis Firestore
        db.collection("users").whereEqualTo("role", "Enseignant")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                        enseignantIds.add(document.getId()); // Ajoute l'ID de l'enseignant
                        enseignantNames.add(document.getString("name")); // Ajoute le nom de l'enseignant
                    }

                    // Adapter le Spinner des enseignants
                    ArrayAdapter<String> adapterEnseignant = new ArrayAdapter<>(getContext(),
                            android.R.layout.simple_spinner_item, enseignantNames);
                    adapterEnseignant.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerEnseignant.setAdapter(adapterEnseignant);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Erreur de récupération des enseignants", Toast.LENGTH_SHORT).show();
                });

        // Récupérer l'ID de l'agent connecté
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db.collection("users").document(currentUserId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        agentId = documentSnapshot.getId(); // Stocker l'ID de l'agent
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Erreur : Impossible de récupérer l'ID de l'agent", Toast.LENGTH_SHORT).show();
                });

        btnSaveAbsence.setOnClickListener(v -> {
            String date = etDate.getText().toString();
            String heure = etHeure.getText().toString();
            String classe = etClasse.getText().toString();
            String justification = spinnerJustification.getSelectedItem().toString();
            int enseignantPosition = spinnerEnseignant.getSelectedItemPosition();

            if (date.isEmpty() || heure.isEmpty() || classe.isEmpty()) {
                Toast.makeText(getContext(), "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                return;
            }

            if (agentId == null || enseignantIds.isEmpty() || enseignantPosition < 0) {
                Toast.makeText(getContext(), "Erreur : Données manquantes", Toast.LENGTH_SHORT).show();
                return;
            }

            String enseignantId = enseignantIds.get(enseignantPosition);

            addAbsenceViewModel.saveAbsence(date, heure, classe, justification, enseignantId, agentId);
        });

        addAbsenceViewModel.getToastMessageLiveData().observe(getViewLifecycleOwner(), message -> {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        });

        return root;
    }
}
