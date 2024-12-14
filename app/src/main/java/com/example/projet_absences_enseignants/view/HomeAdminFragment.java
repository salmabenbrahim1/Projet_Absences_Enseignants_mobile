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
import com.example.projet_absences_enseignants.viewmodel.AdminViewModel;
import com.example.projet_absences_enseignants.viewmodel.AgentViewModel;

public class HomeAdminFragment extends Fragment {

    private AdminViewModel adminViewModel;
    private CardView  cardAddUser, cardGAbsence, cardCalendar, cardRapport,cardReclamations;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Charger le layout du fragment
        View view = inflater.inflate(R.layout.fragment_home_admin, container, false);

        // Initialiser les CardViews
        cardAddUser = view.findViewById(R.id.cardAddUser);
        cardGAbsence = view.findViewById(R.id.cardGAbsence);
        cardCalendar = view.findViewById(R.id.cardCalendar);
        cardRapport = view.findViewById(R.id.cardRapport);
        cardReclamations=view.findViewById(R.id.cardReclamations);

        // Initialiser le ViewModel
        adminViewModel = new ViewModelProvider(this).get(AdminViewModel.class);

        // Définir les actions des CardViews
        cardAddUser.setOnClickListener(v -> adminViewModel.onNavigateTo("add_user"));
        cardGAbsence.setOnClickListener(v -> adminViewModel.onNavigateTo("manage_absences"));
        cardCalendar.setOnClickListener(v -> adminViewModel.onNavigateTo("calendar"));
        cardRapport.setOnClickListener(v -> adminViewModel.onNavigateTo("report"));
        cardReclamations.setOnClickListener(v->adminViewModel.onNavigateTo("reclamations"));

        // Observer les événements de navigation
        adminViewModel.getNavigationEvent().observe(getViewLifecycleOwner(), this::handleNavigation);

        return view;
    }

    // Gérer la navigation en fonction de l'événement
    private void handleNavigation(String destination) {
        Fragment fragment = null;

        switch (destination) {

            case "add_user":
                fragment = new AddUserFragment();
                break;
            case "manage_absences":
                fragment = new GestionAbsenceFragment();
                break;
            case "calendar":
                fragment = new EmploiDuTempsFragment();
                break;
            case "report":
                fragment = new PowerBiFragment();
                break;
            case "reclamations":
                fragment = new ReclamationFragment();
                break;
        }

        if (fragment != null) {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_layout, fragment)
                    .commit();
        }
    }
}
