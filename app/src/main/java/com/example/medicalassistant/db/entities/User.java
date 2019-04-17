package com.example.medicalassistant.db.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.net.Uri;

@Entity
public class User {

    @PrimaryKey(autoGenerate = true)
    public int unique_id;

    //Stored as a string needs to be parsed out later.
    @ColumnInfo (name = "user_image")
    public String user_image;

    @ColumnInfo(name = "first_name")
    public String fname;

    @ColumnInfo(name = "last_name")
    public String lname;

    @ColumnInfo(name = "emergency_contact_name")
    public String eContactName;

    @ColumnInfo(name = "emergency_contact_number")
    public String eContactNum;

    @ColumnInfo(name = "physician_name")
    public String physicianName;

    @ColumnInfo(name = "physician_number")
    public String physicianNum;

    public boolean isCurrent;

    public boolean isCurrent() { return isCurrent; }

    public void setCurrent(boolean current) { isCurrent = current; }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String geteContactName() {
        return eContactName;
    }

    public void seteContactName(String eContactName) {
        this.eContactName = eContactName;
    }

    public String geteContactNum() {
        return eContactNum;
    }

    public void seteContactNum(String eContactNum) {
        this.eContactNum = eContactNum;
    }

    public String getPhysicianName() {
        return physicianName;
    }

    public void setPhysicianName(String physicianName) {
        physicianName = physicianName;
    }

    public String getPhysicianNum() {
        return physicianNum;
    }

    public void setPhysicianNum(String physicianNum) {
        physicianNum = physicianNum;
    }

    @Override
    public String toString() {
        return "User{" +
                " unique_id: " + unique_id +
                " user_image: " + user_image +
                " fname: " + fname +
                " lname: " + lname +
                " econtactname: " + eContactName +
                " econtactnum: " + eContactNum +
                " physicianName: " + physicianName +
                " physicianNum: " + physicianNum + "}";
    }
}
