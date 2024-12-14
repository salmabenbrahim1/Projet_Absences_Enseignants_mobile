package com.example.projet_absences_enseignants.view;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projet_absences_enseignants.R;
import com.example.projet_absences_enseignants.model.Reclamation;
import com.example.projet_absences_enseignants.viewmodel.ReclamationViewModel;

import java.util.List;

public class ReclamationAdapter extends RecyclerView.Adapter<ReclamationAdapter.ReclamationViewHolder> {

    private Context context;
    private List<Reclamation> reclamations;
    private ReclamationViewModel reclamationViewModel;

    public ReclamationAdapter(Context context, List<Reclamation> reclamations, ReclamationViewModel reclamationViewModel) {
        this.context = context;
        this.reclamations = reclamations;
        this.reclamationViewModel = reclamationViewModel;
    }

    @NonNull
    @Override
    public ReclamationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_reclamation, parent, false);
        return new ReclamationViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ReclamationViewHolder holder, int position) {
        Reclamation reclamation = reclamations.get(position);
        SpannableString DescriptionText = new SpannableString("Description de la réclamation: " + reclamation.getJustification());
        DescriptionText.setSpan(new ForegroundColorSpan(Color.parseColor("#03A9F4")), 0, "Description de la réclamation:".length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        DescriptionText.setSpan(new ForegroundColorSpan(Color.BLACK), "Description de la réclamation:".length(), DescriptionText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.tvReclamationDescription.setText(DescriptionText);

        SpannableString DateText = new SpannableString("Date de réclamation: " +reclamation.getDate());
        DateText.setSpan(new ForegroundColorSpan(Color.parseColor("#03A9F4")), 0, "Date de réclamation: ".length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        DateText.setSpan(new ForegroundColorSpan(Color.BLACK), "Date de réclamation: ".length(), DateText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.tvReclamationDate.setText(DateText );

        SpannableString heureText = new SpannableString("Heure de réclamation: " + reclamation.getHeure());
        heureText.setSpan(new ForegroundColorSpan(Color.parseColor("#03A9F4")), 0, "Heure de réclamation:".length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        heureText.setSpan(new ForegroundColorSpan(Color.BLACK), "Heure de réclamation: ".length(), heureText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.tvReclamationHeure.setText(heureText);


        // Bouton de suppression
        holder.btnDelete.setOnClickListener(v -> {
            reclamationViewModel.deleteReclamation(reclamation);
        });
    }



    @Override
    public int getItemCount() {
        return reclamations != null ? reclamations.size() : 0;
    }

    public void setReclamations(List<Reclamation> reclamations) {
        this.reclamations = reclamations;
        notifyDataSetChanged();
    }

    public static class ReclamationViewHolder extends RecyclerView.ViewHolder {

        TextView tvReclamationDescription;
        TextView tvReclamationDate;
        TextView tvReclamationHeure;
        Button btnDelete;

        public ReclamationViewHolder(View itemView) {
            super(itemView);
            tvReclamationDescription = itemView.findViewById(R.id.tvReclamationDescription);
            tvReclamationDate = itemView.findViewById(R.id.tvReclamationDate);
            tvReclamationHeure = itemView.findViewById(R.id.tvReclamationHeure);

            btnDelete = itemView.findViewById(R.id.btnDeleteReclamation);
        }
    }
}
