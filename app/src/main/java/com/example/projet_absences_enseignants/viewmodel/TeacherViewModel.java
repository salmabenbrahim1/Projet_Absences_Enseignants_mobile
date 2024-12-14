package com.example.projet_absences_enseignants.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TeacherViewModel extends ViewModel {

    private final MutableLiveData<String> navigationEvent = new MutableLiveData<>();

    // Méthode appelée pour déclencher un événement de navigation
    public void onNavigateTo(String destination) {
        navigationEvent.setValue(destination);
    }

    // Getter pour l'événement de navigation
    public LiveData<String> getNavigationEvent() {
        return navigationEvent;
    }
}
