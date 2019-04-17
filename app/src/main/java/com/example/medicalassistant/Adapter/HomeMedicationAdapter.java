package com.example.medicalassistant.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.medicalassistant.R;
import com.example.medicalassistant.db.entities.Medication;

import java.util.List;

public class HomeMedicationAdapter extends RecyclerView.Adapter<HomeMedicationAdapter.ViewHolder> {

    private final List<Medication> medications;

    public HomeMedicationAdapter(List<Medication> medications) {
        this.medications = medications;
    }

    @NonNull
    @Override
    public HomeMedicationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.medication_recycler_item,viewGroup,false);

        return new HomeMedicationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeMedicationAdapter.ViewHolder viewHolder, int i) {

        Medication medication = medications.get(i);
        if(medication != null){

            viewHolder.medication = medication;
            viewHolder.tvName.setText(medication.getMedication_Name());
            viewHolder.tvDosage.setText(medication.getDosage());
            viewHolder.tvDescription.setText(medication.getDescription());
        }

    }

    @Override
    public int getItemCount() {
        return medications.size();
    }

    public void addItems(List<Medication> medications) {

        this.medications.clear();
        this.medications.addAll(medications);
        notifyDataSetChanged();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName,tvDosage,tvDescription;
        public Medication medication;
        public View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            tvName = (TextView) itemView.findViewById(R.id.recycler_medname);
            tvDosage = (TextView) itemView.findViewById(R.id.recycler_dosage);
            tvDescription = (TextView) itemView.findViewById(R.id.recycler_description);
        }
    }
}
