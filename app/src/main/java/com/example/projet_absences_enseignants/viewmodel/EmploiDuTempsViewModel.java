package com.example.projet_absences_enseignants.viewmodel;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.projet_absences_enseignants.model.EmploiDuTemps;
import com.google.firebase.firestore.FirebaseFirestore;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class EmploiDuTempsViewModel extends ViewModel {

    private MutableLiveData<List<EmploiDuTemps>> emploiDuTempsList = new MutableLiveData<>();
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    public LiveData<List<EmploiDuTemps>> getEmploiDuTempsList() {
        return emploiDuTempsList;
    }

    public void loadEmploisDuTempsFromFile(Uri fileUri, Context context) {
        List<EmploiDuTemps> emploiDuTemps = new ArrayList<>();

        try {
            InputStream inputStream = context.getContentResolver().openInputStream(fileUri);
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {

                if (row.getCell(0) != null && row.getCell(1) != null && row.getCell(2) != null && row.getCell(3) != null) {
                    String jour = getStringCellValue(row.getCell(0));
                    String heureDebut = getStringCellValue(row.getCell(2));
                    String heureFin = getStringCellValue(row.getCell(3));
                    String matiere = getStringCellValue(row.getCell(1));

                    // Créer un objet EmploiDuTemps et l'ajouter à la liste
                    EmploiDuTemps et = new EmploiDuTemps(jour,matiere, heureDebut, heureFin);
                    emploiDuTemps.add(et);
                }
            }

            // Mettre à jour le LiveData avec la liste traitée
            emploiDuTempsList.postValue(emploiDuTemps);

            // Enregistrement dans Firestore
            for (EmploiDuTemps et : emploiDuTemps) {
                firestore.collection("emplois_du_temps")
                        .add(et.toMap()) // Utilisation de la méthode toMap() pour Firestore
                        .addOnSuccessListener(documentReference -> Log.d("Firestore", "Document ajouté"))
                        .addOnFailureListener(e -> {
                            Log.e("Firestore", "Erreur lors de l'ajout", e);
                            Toast.makeText(context, "Erreur lors de l'ajout à Firestore", Toast.LENGTH_SHORT).show();
                        });
            }

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Une erreur est survenue lors du chargement du fichier", Toast.LENGTH_SHORT).show();
        }
    }

    // Fonction pour obtenir une valeur de chaîne de caractères
    private String getStringCellValue(org.apache.poi.ss.usermodel.Cell cell) {
        if (cell.getCellType() == org.apache.poi.ss.usermodel.CellType.STRING) {
            return cell.getStringCellValue();
        } else if (cell.getCellType() == org.apache.poi.ss.usermodel.CellType.NUMERIC) {

            if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {

                return convertExcelTimeToString(cell.getNumericCellValue());
            } else {
                // Si c'est un nombre classique, le convertir en chaîne
                return String.valueOf(cell.getNumericCellValue());
            }
        }
        return "";
    }

    // Convertir un nombre décimal représentant une heure en format "HH:mm"
    private String convertExcelTimeToString(double numericValue) {
        int totalMinutes = (int) (numericValue * 24 * 60); // Conversion en minutes
        int hours = totalMinutes / 60;
        int minutes = totalMinutes % 60;
        return String.format("%02d:%02d", hours, minutes);
    }
}