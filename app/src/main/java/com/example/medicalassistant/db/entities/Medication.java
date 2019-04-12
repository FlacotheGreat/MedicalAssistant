package com.example.medicalassistant.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Medication {

    @PrimaryKey(autoGenerate = true)
    public int unique_id_Medication;

    public String medication_Name;

    public String frequency;

    public String dosage;

    public String quantity;

    public String time;

    public String last_taken;

    public String description;

    public boolean taken;

    public String duration;

    public String day;

    public boolean daily;

    public boolean Sunday;

    public boolean Monday;

    public boolean Tuesday;

    public boolean Wednesday;

    public boolean Thursday;

    public boolean Friday;

    public boolean Saturday;

    public String getMedication_Name() {
        return medication_Name;
    }

    public void setMedication_Name(String medication_Name) {
        this.medication_Name = medication_Name; }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLast_taken() {
        return last_taken;
    }

    public void setLast_taken(String last_taken) {
        this.last_taken = last_taken;
    }

    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin in orci efficitur, porttitor ante non, aliquet enim.";
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public boolean isDaily() {
        return daily;
    }

    public void setDaily(boolean daily) {
        this.daily = daily;
    }

    public boolean isSunday() {
        return Sunday;
    }

    public void setSunday(boolean sunday) {
        Sunday = sunday;
    }

    public boolean isMonday() {
        return Monday;
    }

    public void setMonday(boolean monday) {
        Monday = monday;
    }

    public boolean isTuesday() {
        return Tuesday;
    }

    public void setTuesday(boolean tuesday) {
        Tuesday = tuesday;
    }

    public boolean isWednesday() {
        return Wednesday;
    }

    public void setWednesday(boolean wednesday) {
        Wednesday = wednesday;
    }

    public boolean isThursday() {
        return Thursday;
    }

    public void setThursday(boolean thursday) {
        Thursday = thursday;
    }

    public boolean isFriday() {
        return Friday;
    }

    public void setFriday(boolean friday) {
        Friday = friday;
    }

    public boolean isSaturday() {
        return Saturday;
    }

    public void setSaturday(boolean saturday) {
        Saturday = saturday;
    }

    @Override
    public String toString() {
        return "User{" +
                " unique_id_Medication: " + unique_id_Medication +
                " medication_Name: " + medication_Name +
                " frequency: " + frequency +
                " dosage: " + dosage +
                " time: " + time +
                " last_taken: " + last_taken +
                " taken: " + taken +
                " daily: " + daily +
                " Sunday: " + Sunday + " }";
    }
}
