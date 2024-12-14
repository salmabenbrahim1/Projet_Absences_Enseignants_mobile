package com.example.projet_absences_enseignants.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PowerBiViewModel extends ViewModel {
    private final MutableLiveData<String> reportUrl = new MutableLiveData<>();

    public PowerBiViewModel() {
        // Initialisez l'URL du rapport Power BI
        reportUrl.setValue("https://app.powerbi.com/reportEmbed?reportId=354f7756-5f9a-4e4d-8464-58e63a05c73c&autoAuth=true&ctid=604f1a96-cbe8-43f8-abbf-f8eaf5d85730");
    }

    public LiveData<String> getReportUrl() {
        return reportUrl;
    }
}
