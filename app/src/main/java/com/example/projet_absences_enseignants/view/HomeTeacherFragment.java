package com.example.projet_absences_enseignants.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.projet_absences_enseignants.R;
import com.example.projet_absences_enseignants.viewmodel.TeacherViewModel;

public class HomeTeacherFragment extends Fragment {

    private TeacherViewModel teacherViewModel;
    private CardView  cardAddAbsence, cardGAbsence, cardNotifications;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Charger le layout du fragment
        View view = inflater.inflate(R.layout.fragment_home_teacher, container, false);

        // Initialiser les CardViews
        cardAddAbsence = view.findViewById(R.id.cardAddAbsence);
        cardGAbsence = view.findViewById(R.id.cardGAbsence);
        cardNotifications = view.findViewById(R.id.cardNotifications);

        // Initialiser le ViewModel
        teacherViewModel = new ViewModelProvider(this).get(TeacherViewModel.class);

        // Définir les actions des CardViews
        cardAddAbsence.setOnClickListener(v -> teacherViewModel.onNavigateTo("add_absence"));
        cardGAbsence.setOnClickListener(v -> teacherViewModel.onNavigateTo("manage_absences"));
        cardNotifications.setOnClickListener(v -> teacherViewModel.onNavigateTo("notifications"));

        // Observer les événements de navigation
        teacherViewModel.getNavigationEvent().observe(getViewLifecycleOwner(), this::handleNavigation);

        return view;
    }

    // Gérer la navigation en fonction de l'événement
    private void handleNavigation(String destination) {
        Fragment fragment = null;

        switch (destination) {


            case "add_absence":
                fragment = new AddReclamationFragment();
                break;

            case "manage_absences":
                fragment = new ListAbsenceFragment();
                break;


        }

        if (fragment != null) {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_layout, fragment)
                    .addToBackStack(null) // Ajouter à la pile pour permettre un retour
                    .commit();
        }
    }
}
