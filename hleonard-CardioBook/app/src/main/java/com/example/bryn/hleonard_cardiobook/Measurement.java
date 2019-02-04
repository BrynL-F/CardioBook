package com.example.bryn.hleonard_cardiobook;

import android.icu.util.Measure;
import android.os.Parcel;
import android.os.Parcelable;

import java.net.PasswordAuthentication;
import java.security.PrivilegedActionException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.UUID;

public class Measurement implements Parcelable {
    private String date;
    private String time;
    private Integer SystolicPressure;
    private Integer DiastolicPressure;
    private Integer HeartRate;
    private String Comment;
    private String mID = UUID.randomUUID().toString();


    public Measurement(Parcel in) throws ParseException {
        mID = in.readString();
        date = in.readString();
        SystolicPressure = in.readInt();
        DiastolicPressure = in.readInt();
        HeartRate = in.readInt();
        Comment = in.readString();
    }

    public Measurement() {
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        date = dateFormat.format(new Date());
        dateFormat = new SimpleDateFormat("hh:mm:ss");
        time = dateFormat.format(new Date());
        SystolicPressure = 0;
        DiastolicPressure = 0;
        HeartRate = 0;
        Comment = "";
    }

    public static Measurement readFromParcel(Parcel in){
        String mID1 = in.readString();
        String Date1 = in.readString();
        String Time1 = in.readString();
        Integer SystolicPressure1 = in.readInt();
        Integer DiastolicPressure1 = in.readInt();
        Integer HeartRate1 = in.readInt();
        String Comment1 = in.readString();
        return new Measurement(Date1, Time1, SystolicPressure1, DiastolicPressure1,
                HeartRate1, Comment1, mID1);
    }



    public Measurement(String  date, String time, Integer systolicPressure, Integer diastolicPressure,
                       Integer heartRate, String comment, String mID) {
        this.date = date;
        this.time = time;
        SystolicPressure = systolicPressure;
        DiastolicPressure = diastolicPressure;
        HeartRate = heartRate;
        Comment = comment;
        this.mID = mID;
    }


    public String getDate(){ return this.date; }

    public void setDate(String dateString) {
        this.date = dateString;
    }

    public String getTime(){ return this.time; }

    public void setTime(String timeString) {
        this.time = timeString;
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

    public String getmID() {
        return mID;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    public static final Parcelable.Creator<Measurement> CREATOR = new Parcelable.Creator<Measurement>() {
        public Measurement createFromParcel(Parcel in) {
            return readFromParcel(in);
        }
        public Measurement[] newArray(int size) {
            return new Measurement[size];
        }
    };


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mID);
        dest.writeString(this.date);
        dest.writeString(this.time);
        dest.writeInt(SystolicPressure);
        dest.writeInt(DiastolicPressure);
        dest.writeInt(HeartRate);
        dest.writeString(Comment);
    }




}
