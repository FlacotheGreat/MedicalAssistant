package com.example.medicalassistant.DialogFragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TimePicker;

import com.example.medicalassistant.Fragments.TimePickerFragment;
import com.example.medicalassistant.R;
import com.example.medicalassistant.db.AppDatabase;
import com.example.medicalassistant.db.entities.Medication;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MedicationDialogFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private View root;
    private TextInputEditText medicationnametxt, dosagetxt,quantitytxt,lastTakentxt,frequencytxt, durationtext;
    private CheckBox dailybox,sundaybox,mondaybox,tuesdaybox,wednesdaybox,thursdaybox,fridaybox,saturdaybox;
    private Button takepictureButton, savebtn, timebtn;

    private String timeOfDay;
    private Medication medication;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_add_medication_dialog, container, false);
        Toolbar toolbar = root.findViewById(R.id.medicationToolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close_black_24dp);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        toolbar.setTitle("Add Medication");

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public void onResume() {

        medicationnametxt = root.findViewById(R.id.medication_name);
        dosagetxt = root.findViewById(R.id.medication_dosage);
        quantitytxt = root.findViewById(R.id.medication_quatity);
        lastTakentxt = root.findViewById(R.id.medication_last_taken);
        frequencytxt = root.findViewById(R.id.medication_frequency);
        durationtext = root.findViewById(R.id.medication_duration);

        takepictureButton = root.findViewById(R.id.medication_takepicture);
        savebtn = root.findViewById(R.id.medication_savebutton);
        timebtn = root.findViewById(R.id.timeButton);


        dailybox = root.findViewById(R.id.daily_checkbox);
        sundaybox = root.findViewById(R.id.sunday_checkbox);
        mondaybox = root.findViewById(R.id.monday_checkbox);
        tuesdaybox = root.findViewById(R.id.tuesday_checkbox);
        wednesdaybox = root.findViewById(R.id.wednesday_checkbox);
        thursdaybox = root.findViewById(R.id.thursday_checkbox);
        fridaybox = root.findViewById(R.id.friday_checkbox);
        saturdaybox = root.findViewById(R.id.saturday_checkbox);

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                medication = new Medication();

                medication.setMedication_Name(medicationnametxt.getText().toString());
                medication.setDosage(dosagetxt.getText().toString());
                medication.setQuantity(quantitytxt.getText().toString());
                medication.setTime(getTime());
                medication.setDescription("");
                medication.setLast_taken(lastTakentxt.getText().toString());
                medication.setFrequency(Integer.parseInt(frequencytxt.getText().toString()));
                medication.setDuration(durationtext.getText().toString());

                if(dailybox.isChecked()){
                    medication.setDaily(true);
                    medication.setSunday(true);
                    medication.setMonday(true);
                    medication.setTuesday(true);
                    medication.setWednesday(true);
                    medication.setThursday(true);
                    medication.setFriday(true);
                    medication.setSaturday(true);
                } else if(!dailybox.isChecked()) {
                    Log.d("TestCheckBoxes","ReportCheckBoxes: " + String.valueOf(saturdaybox.isChecked()) + " "
                                                                        + String.valueOf(sundaybox.isChecked()));
                    medication.setSaturday(saturdaybox.isChecked());
                    medication.setFriday(fridaybox.isChecked());
                    medication.setThursday(thursdaybox.isChecked());
                    medication.setWednesday(wednesdaybox.isChecked());
                    medication.setTuesday(tuesdaybox.isChecked());
                    medication.setMonday(mondaybox.isChecked());
                    medication.setSunday(sundaybox.isChecked());

                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("TestSavebtn", "Entering on click for save button");

                        Log.d("TestMedication","Medication: " + medication);
                        AppDatabase.getInstance(getContext()).medicationDAO().insertMedication(medication);

                        Log.d("TestMedicationDB","Database Contains:" +AppDatabase.getInstance(getContext()).medicationDAO().getAllMedication().toString());
                    }
                }).start();

                dismiss();
            }

        });



        timebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerFragment timepicker = new TimePickerFragment();
                timepicker.setListener(MedicationDialogFragment.this);
                timepicker.show(getActivity().getSupportFragmentManager(), "time picker");
                Log.d("TestSetTime","setting text to" +timeOfDay);
            }
        });
        super.onResume();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    public static String getTime(){

        Date date = new Date();
        String formatDate = "hh:mm:ss a";
        DateFormat dateFormat = new SimpleDateFormat(formatDate);
        return dateFormat.format(date);
    }

    private String setTimeOfday(String time){
        Log.d("TestSetTime", time);
        return timeOfDay = time;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        //lastTakentxt = (TextInputEditText) getView().findViewById(R.id.medication_last_taken);
        lastTakentxt.setText(hourOfDay+":"+minute);

    }
}
