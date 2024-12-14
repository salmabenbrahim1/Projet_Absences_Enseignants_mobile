package com.example.projet_absences_enseignants.view;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projet_absences_enseignants.R;
import com.example.projet_absences_enseignants.model.Absence;
import com.example.projet_absences_enseignants.viewmodel.GestionAbsenceViewModel;

public class GestionAbsenceFragment extends Fragment implements AbsenceAdapter.OnItemClickListener, AbsenceAdapter.OnDeleteClickListener, AbsenceAdapter.OnModifyClickListener {

    private RecyclerView recyclerView;
    private AbsenceAdapter absenceAdapter;
    private GestionAbsenceViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_gestion_absence, container, false);

        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Initialisation du ViewModel
        viewModel = new ViewModelProvider(this).get(GestionAbsenceViewModel.class);

        // Initialisation de l'adaptateur avec le listener de modification
        absenceAdapter = new AbsenceAdapter(viewModel.getAbsenceList(), this, this, this);
        recyclerView.setAdapter(absenceAdapter);

        // Observer les données pour mettre à jour l'UI lorsque la liste des absences change
        viewModel.getAbsences().observe(getViewLifecycleOwner(), absences -> {
            absenceAdapter.notifyDataSetChanged();
        });

        // Charger les absences depuis Firebase
        viewModel.getAbsencesFromFirebase();

        return rootView;
    }

    @Override
    public void onDeleteClick(Absence absence) {
        if (getContext() != null) {
            new AlertDialog.Builder(getContext())
                    .setTitle("Confirmation")
                    .setMessage("Êtes-vous sûr de vouloir supprimer cette absence ?")
                    .setPositiveButton("Oui", (dialog, which) -> {
                        // Passer le contexte à la méthode de suppression
                        viewModel.deleteAbsence(absence, getContext());
                    })
                    .setNegativeButton("Non", null)
                    .show();
        }
    }
    public void onModifyClick(Absence absence) {
        // Créer la vue pour le dialog
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View dialogView = inflater.inflate(R.layout.dialog_modify_absence, null);

        // Récupérer les champs de saisie
        EditText etEnseignement = dialogView.findViewById(R.id.etEnseignement);
        EditText etStatut = dialogView.findViewById(R.id.etStatut);
        EditText etDate = dialogView.findViewById(R.id.etDate);
        EditText etHeure = dialogView.findViewById(R.id.etHeure);
        EditText etClasse = dialogView.findViewById(R.id.etClasse);
        Button btnSave = dialogView.findViewById(R.id.btnSave);

        // Initialiser les champs avec les données existantes de l'absence
        etEnseignement.setText(absence.getEnseignement());
        etStatut.setText(absence.getStatut());
        etDate.setText(absence.getDate());
        etHeure.setText(absence.getHeure());
        etClasse.setText(absence.getClasse());

        // Créer l'AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Modifier l'absence")
                .setView(dialogView)
                .setNegativeButton("Annuler", (dialog, which) -> dialog.dismiss())
                .setPositiveButton("OK", null); // Le bouton "OK" sera géré par le listener du bouton "Sauvegarder"

        // Afficher l'AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();

        // Gérer l'événement de "Sauvegarder"
        btnSave.setOnClickListener(v -> {
            // Récupérer les nouvelles valeurs des champs
            String newEnseignement = etEnseignement.getText().toString();
            String newStatut = etStatut.getText().toString();
            String newDate = etDate.getText().toString();
            String newHeure = etHeure.getText().toString();
            String newClasse = etClasse.getText().toString();

            // Modifier l'objet Absence avec les nouvelles valeurs
            absence.setEnseignement(newEnseignement);
            absence.setStatut(newStatut);
            absence.setDate(newDate);
            absence.setHeure(newHeure);
            absence.setClasse(newClasse);

            // Passer les nouvelles données au ViewModel pour mettre à jour l'absence
            viewModel.updateAbsence(absence);

            // Afficher un message de succès et fermer le dialog
            Toast.makeText(getContext(), "Absence modifiée avec succès", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });
    }

    @Override
    public void onItemClick(Absence absence) {
        Toast.makeText(getContext(), "Absence: " + absence.getEnseignement(), Toast.LENGTH_SHORT).show();
    }
}