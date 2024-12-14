package com.example.projet_absences_enseignants.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.projet_absences_enseignants.R;
import com.example.projet_absences_enseignants.viewmodel.AddUserViewModel;

public class AddUserFragment extends Fragment {

    private EditText nameInput, emailInput, passwordInput;
    private Button signupButton;
    private ProgressBar progressBar;
    private Spinner roleSpinner;

    private AddUserViewModel addUserViewModel;

    public AddUserFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_user, container, false);

        nameInput = view.findViewById(R.id.nameInput);
        emailInput = view.findViewById(R.id.emailInput);
        passwordInput = view.findViewById(R.id.passwordInput);
        signupButton = view.findViewById(R.id.signupButton);
        progressBar = view.findViewById(R.id.progressBar);
        roleSpinner = view.findViewById(R.id.roleSpinner);

        // Initialisation du ViewModel
        addUserViewModel = new ViewModelProvider(this).get(AddUserViewModel.class);

        // Remplir le Spinner avec les rôles
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.role_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(adapter);

        // Observer les messages de succès et d'erreur
        addUserViewModel.getSuccessMessage().observe(getViewLifecycleOwner(), message -> {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        });

        addUserViewModel.getErrorMessage().observe(getViewLifecycleOwner(), message -> {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        });



        // Action pour le bouton d'inscription
        signupButton.setOnClickListener(v -> createUser());

        return view;
    }

    private void createUser() {
        String name = nameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String role = roleSpinner.getSelectedItem().toString();

        // Vérification que tous les champs sont remplis
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(getContext(), "Veuillez entrer tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        // Vérification du format de l'email
        if (!isValidEmail(email)) {
            Toast.makeText(getContext(), "L'email n'est pas valide", Toast.LENGTH_SHORT).show();
            return;
        }

        // Affichage de la ProgressBar
        progressBar.setVisibility(View.VISIBLE);

        addUserViewModel.addUser(name, email, password, role);
    }

    // Fonction pour vérifier si l'email est valide
    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
