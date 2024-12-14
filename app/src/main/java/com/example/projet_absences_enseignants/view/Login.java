package com.example.projet_absences_enseignants.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.projet_absences_enseignants.MainActivity;
import com.example.projet_absences_enseignants.R;
import com.example.projet_absences_enseignants.viewmodel.LoginViewModel;

public class Login extends AppCompatActivity {

    private EditText emailInput, passwordInput;
    private Button loginButton;
    private TextView signupLink;
    private ProgressBar progressBar;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialisation des vues
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        progressBar = findViewById(R.id.progressBar);

        // Initialisaation du ViewModel
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        // Action pour le bouton de connexion
        loginButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(Login.this, "Veuillez entrer tous les champs", Toast.LENGTH_SHORT).show();
                return;
            }
            progressBar.setVisibility(View.VISIBLE);
            loginViewModel.loginUser(email, password);
        });

        // Observer les changements dans le ViewModel
        loginViewModel.getIsLoginSuccessful().observe(this, isSuccessful -> {
            progressBar.setVisibility(View.GONE);
            if (isSuccessful) {
                loginViewModel.getUserRole().observe(this, role -> {
                    navigateBasedOnRole(role);
                });
            } else {
                Toast.makeText(Login.this, "Erreur de connexion", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void navigateBasedOnRole(String role) {
        Intent intent = new Intent(Login.this, MainActivity.class);
        intent.putExtra("ROLE", role);  // Passer le rôle de l'utilisateur à MainActivity
        startActivity(intent);
        finish(); // Terminer l'activité Login
    }
}
