package com.example.projet_absences_enseignants.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projet_absences_enseignants.R;
import com.example.projet_absences_enseignants.viewmodel.ReclamationViewModel;
import com.example.projet_absences_enseignants.model.Reclamation;

import java.util.List;

public class ReclamationFragment extends Fragment {

    private ReclamationViewModel reclamationViewModel;
    private RecyclerView recyclerView;
    private ReclamationAdapter reclamationAdapter;

    public ReclamationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reclamation, container, false);

        // Initialisation du ViewModel
        reclamationViewModel = new ViewModelProvider(this).get(ReclamationViewModel.class);

        // Initialisation du RecyclerView et de l'adaptateur
        recyclerView = rootView.findViewById(R.id.recyclerViewReclamations);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        reclamationAdapter = new ReclamationAdapter(getContext(), null, reclamationViewModel);
        recyclerView.setAdapter(reclamationAdapter);

        // Observer les changements dans la liste des réclamations
        reclamationViewModel.getReclamations().observe(getViewLifecycleOwner(), new Observer<List<Reclamation>>() {
            @Override
            public void onChanged(List<Reclamation> reclamations) {
                if (reclamations != null && !reclamations.isEmpty()) {
                    reclamationAdapter.setReclamations(reclamations);
                }
            }
        });

        return rootView;
    }
}
