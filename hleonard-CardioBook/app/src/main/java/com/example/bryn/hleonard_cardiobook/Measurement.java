package com.example.bryn.hleonard_cardiobook;

import android.icu.util.Measure;
import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.UUID;

public class Measurement implements Parcelable {
    private Date dateTime;
    private Integer SystolicPressure;
    private Integer DiastolicPressure;
    private Integer HeartRate;
    private String Comment;
    private Date dateTimeCreated;
    private String mID;


    public Measurement(Parcel in){
        String mID = in.readString();
        String dateTimeCreatedString = in.readString();
        String dateTimeString = in.readString();
        SystolicPressure = in.readInt();
        DiastolicPressure = in.readInt();
        HeartRate = in.readInt();
        Comment = in.readString();
    }

    public Measurement() {
        mID = UUID.randomUUID().toString();
        dateTimeCreated = new Date();
        dateTime = dateTimeCreated;
        SystolicPressure = 0;
        DiastolicPressure = 0;
        HeartRate = 0;
        Comment = "";
    }
/*
    public Measurement(Date dateTime, Integer systolicPressure, Integer diastolicPressure, Integer heartRate, String comment, Date dateTimeCreated, String mID) {
        this.dateTime = dateTime;
        SystolicPressure = systolicPressure;
        DiastolicPressure = diastolicPressure;
        HeartRate = heartRate;
        Comment = comment;
        this.dateTimeCreated = dateTimeCreated;
        this.mID = mID;
    }
*/
    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getSystolicPressure() {
        return SystolicPressure;
    }

    public void setSystolicPressure(Integer systolicPressure) {
        SystolicPressure = systolicPressure;
    }

    public Integer getDiastolicPressure() {
        return DiastolicPressure;
    }

    public void setDiastolicPressure(Integer diastolicPressure) {
        DiastolicPressure = diastolicPressure;
    }

    public Integer getHeartRate() {
        return HeartRate;
    }

    public void setHeartRate(Integer heartRate) {
        HeartRate = heartRate;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public Date getTimeCreated() {
        return dateTimeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.dateTimeCreated = timeCreated;
    }

    public String getmID() {
        return mID;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Measurement createFromParcel(Parcel in) {
            return new Measurement(in);
        }

        public Measurement[] newArray(int size) {
            return new Measurement[size];
        }
    };


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        String dateTimeCreatedString;
        String dateTimeString;
        dest.writeString(mID);
        dest.writeString(this.dateTimeCreatedToString());
        dest.writeString(this.dateTimeToString());
        dest.writeInt(SystolicPressure);
        dest.writeInt(DiastolicPressure);
        dest.writeInt(HeartRate);
        dest.writeString(Comment);
    }

    private String dateTimeCreatedToString(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        return dateFormat.format(dateTimeCreated);
    }

    private String dateTimeToString(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        return dateFormat.format(dateTime);
    }



}
