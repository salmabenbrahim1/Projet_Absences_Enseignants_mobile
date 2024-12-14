package com.example.projet_absences_enseignants;

import static androidx.core.content.ContentProviderCompat.requireContext;
import static androidx.core.view.ViewCompat.setBackgroundTintList;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.projet_absences_enseignants.view.HomeAdminFragment;
import com.example.projet_absences_enseignants.view.HomeAgentDeSuiviFragment;
import com.example.projet_absences_enseignants.view.HomeTeacherFragment;
import com.example.projet_absences_enseignants.view.Login;
import com.example.projet_absences_enseignants.view.ProfilFragment;
import com.example.projet_absences_enseignants.viewmodel.AuthViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity {

    private AuthViewModel authViewModel;
    private BottomNavigationView bottomNavigationView;
    private String role;
    DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); // Désactiver le titre par défaut

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        authViewModel.getUserRole().observe(this, userRole -> {
            if (userRole != null) {
                role = userRole;
                if (role.equals("Erreur")) {
                    Toast.makeText(MainActivity.this, "Erreur : Rôle introuvable", Toast.LENGTH_SHORT).show();
                } else {
                    // Naviguer vers le fragment correspondant au rôle
                    navigateBasedOnRole(role);
                }
            } else {
                Toast.makeText(MainActivity.this, "Utilisateur non connecté", Toast.LENGTH_SHORT).show();
            }
        });

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (role != null) {
                    if (item.getItemId() == R.id.home) {
                        if (role.equals("Agent de Suivi")) {
                            replaceFragment(new HomeAgentDeSuiviFragment());
                        } else if (role.equals("Enseignant")) {
                            replaceFragment(new HomeTeacherFragment());
                        } else if (role.equals("Administration")) {
                            replaceFragment(new HomeAdminFragment());
                        }
                    } else if (item.getItemId() == R.id.profile) {
                        // Gérer la sélection du profil
                        if (role.equals("Agent de Suivi")) {
                            replaceFragment(new ProfilFragment());
                        } else if (role.equals("Enseignant")) {
                            replaceFragment(new ProfilFragment());
                        } else if (role.equals("Administration")) {
                            replaceFragment(new ProfilFragment());
                        }
                    } else if (item.getItemId() == R.id.logout) {
                        // Déconnexion
                        authViewModel.logout();
                        Toast.makeText(MainActivity.this, "Déconnexion réussie", Toast.LENGTH_SHORT).show();

                        // Redirection vers la page de login
                        Intent intent = new Intent(MainActivity.this, Login.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                }
                return true;
            }
        });
    }

    private void navigateBasedOnRole(String role) {
        Fragment fragment;
        switch (role) {
            case "Agent de Suivi":
                fragment = new HomeAgentDeSuiviFragment();
                break;
            case "Administration":
                fragment = new HomeAdminFragment();
                break;
            case "Enseignant":
                fragment = new HomeTeacherFragment();
                break;
            default:
                Toast.makeText(MainActivity.this, "Rôle non défini", Toast.LENGTH_SHORT).show();
                return;
        }
        replaceFragment(fragment);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, fragment);
        transaction.commit();
    }
}
