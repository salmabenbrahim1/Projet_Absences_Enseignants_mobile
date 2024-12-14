package com.example.projet_absences_enseignants.view;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.example.projet_absences_enseignants.R;
import com.example.projet_absences_enseignants.viewmodel.AddReclamationViewModel;


public class AddReclamationFragment extends Fragment {

    private AddReclamationViewModel viewModel;
    private EditText etDate, etHeure, etJustification;
    private Button btnSaveReclamation;

    public AddReclamationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_add_reclamation, container, false);


        etDate = rootView.findViewById(R.id.etDateReclamation);
        etHeure = rootView.findViewById(R.id.etHeureReclamation);
        etJustification = rootView.findViewById(R.id.etJustificationReclamation);
        btnSaveReclamation = rootView.findViewById(R.id.btnSaveReclamation);


        viewModel = new ViewModelProvider(this).get(AddReclamationViewModel.class);

        // Observer l'ajout de réclamation
        viewModel.getIsReclamationAdded().observe(getViewLifecycleOwner(), success -> {
            if (success) {
                Toast.makeText(getContext(), "Réclamation ajoutée avec succès", Toast.LENGTH_SHORT).show();
                clearFields();
            } else {
                Toast.makeText(getContext(), "Échec de l'ajout de la réclamation", Toast.LENGTH_SHORT).show();
            }
        });

        // Écouteur pour le bouton Enregistrer
        btnSaveReclamation.setOnClickListener(v -> {
            String date = etDate.getText().toString().trim();
            String heure = etHeure.getText().toString().trim();
            String justification = etJustification.getText().toString().trim();

            if (validateInputs(date, heure, justification)) {
                viewModel.addReclamation(date, heure, justification);
            } else {
                Toast.makeText(getContext(), "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    // Méthode pour valider les champs
    private boolean validateInputs(String date, String heure, String justification) {
        return !date.isEmpty() && !heure.isEmpty() && !justification.isEmpty();
    }

    // Méthode pour réinitialiser les champs
    private void clearFields() {
        etDate.setText("");
        etHeure.setText("");
        etJustification.setText("");
    }
}
