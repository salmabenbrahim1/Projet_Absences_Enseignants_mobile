package com.example.projet_absences_enseignants.view;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.projet_absences_enseignants.R;
import com.example.projet_absences_enseignants.model.Absence;

import java.util.List;

public class AbsenceAdapter extends RecyclerView.Adapter<AbsenceAdapter.AbsenceViewHolder> {

    private List<Absence> absenceList;
    private OnItemClickListener listener;
    private OnDeleteClickListener deleteListener;
    private OnModifyClickListener modifyListener;

    public AbsenceAdapter(List<Absence> absenceList, OnItemClickListener listener, OnDeleteClickListener deleteListener, OnModifyClickListener modifyListener) {
        this.absenceList = absenceList;
        this.listener = listener;
        this.deleteListener = deleteListener;
        this.modifyListener = modifyListener;
    }

    @Override
    public AbsenceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_absence, parent, false);
        return new AbsenceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AbsenceViewHolder holder, int position) {
        Absence absence = absenceList.get(position);

        // Enseignement
        SpannableString enseignementText = new SpannableString("Enseignant: " + absence.getEnseignement());
        enseignementText.setSpan(new ForegroundColorSpan(Color.parseColor("#03A9F4")), 0, "Enseignant: ".length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        enseignementText.setSpan(new ForegroundColorSpan(Color.BLACK), "Enseignant: ".length(), enseignementText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.tvEnseignement.setText(enseignementText);

        // Justification
        String justificationText = "Justification: " + absence.getStatut();
        SpannableString spannable = new SpannableString(justificationText);
        spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#03A9F4")), 0, "Justification: ".length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        if ("Justifiée".equals(absence.getStatut())) {
            spannable.setSpan(new ForegroundColorSpan(Color.BLACK), "Justification: ".length(), spannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else if ("Non Justifiée".equals(absence.getStatut())) {
            spannable.setSpan(new ForegroundColorSpan(Color.RED), "Justification: ".length(), spannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        holder.tvJustification.setText(spannable);

        // Date
        SpannableString dateText = new SpannableString("Date: " + absence.getDate());
        dateText.setSpan(new ForegroundColorSpan(Color.parseColor("#03A9F4")), 0, "Date: ".length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        dateText.setSpan(new ForegroundColorSpan(Color.BLACK), "Date: ".length(), dateText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.tvDate.setText(dateText);

        // Heure
        SpannableString heureText = new SpannableString("Heure: " + absence.getHeure());
        heureText.setSpan(new ForegroundColorSpan(Color.parseColor("#03A9F4")), 0, "Heure: ".length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        heureText.setSpan(new ForegroundColorSpan(Color.BLACK), "Heure: ".length(), heureText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.tvHeure.setText(heureText);

        // Classe
        SpannableString classeText = new SpannableString("Classe: " + absence.getClasse());
        classeText.setSpan(new ForegroundColorSpan(Color.parseColor("#03A9F4")), 0, "Classe: ".length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        classeText.setSpan(new ForegroundColorSpan(Color.BLACK), "Classe: ".length(), classeText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.tvClasse.setText(classeText);

        // Listener pour les clics sur l'élément de la liste
        holder.itemView.setOnClickListener(v -> listener.onItemClick(absence));

        // Listener pour le bouton de suppression
        holder.btnDeleteAbsence.setOnClickListener(v -> deleteListener.onDeleteClick(absence));

        // Listener pour le bouton de modification
        holder.btnModifyAbsence.setOnClickListener(v -> modifyListener.onModifyClick(absence));
    }

    @Override
    public int getItemCount() {
        return absenceList.size();
    }

    public static class AbsenceViewHolder extends RecyclerView.ViewHolder {
        TextView tvEnseignement, tvJustification, tvDate, tvHeure, tvClasse;
        Button btnDeleteAbsence, btnModifyAbsence;

        public AbsenceViewHolder(View itemView) {
            super(itemView);
            tvEnseignement = itemView.findViewById(R.id.tvEnseignement);
            tvJustification = itemView.findViewById(R.id.tvJustification);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvHeure = itemView.findViewById(R.id.tvHeure);
            tvClasse = itemView.findViewById(R.id.tvClasse);
            btnDeleteAbsence = itemView.findViewById(R.id.btnDeleteAbsence);
            btnModifyAbsence = itemView.findViewById(R.id.btnModifyAbsence);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Absence absence);
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(Absence absence);
    }

    public interface OnModifyClickListener {
        void onModifyClick(Absence absence);
    }
}
