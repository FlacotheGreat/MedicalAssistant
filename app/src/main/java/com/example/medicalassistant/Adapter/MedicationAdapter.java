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

public class MedicationAdapter extends RecyclerView.Adapter<MedicationAdapter.ViewHolder> {

    private final List<Medication> medications;

    public MedicationAdapter(List<Medication> medications) {
        this.medications = medications;
    }

    @NonNull
    @Override
    public MedicationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.medication_recycler_item,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicationAdapter.ViewHolder viewHolder, int i) {

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



