package com.example.bryn.hleonard_cardiobook;

import android.icu.util.Measure;
import android.os.Parcel;
import android.os.Parcelable;

import java.net.PasswordAuthentication;
import java.security.PrivilegedActionException;
import java.text.DateFormat;
import java.text.ParseException;
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
    private String mID = UUID.randomUUID().toString();


    public Measurement(Parcel in) throws ParseException {
        mID = in.readString();
        dateTime = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").parse(in.readString());
        dateTimeCreated = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").parse(in.readString());
        SystolicPressure = in.readInt();
        DiastolicPressure = in.readInt();
        HeartRate = in.readInt();
        Comment = in.readString();
    }

    public Measurement() {
        dateTimeCreated = new Date();
        dateTime = dateTimeCreated;
        SystolicPressure = 0;
        DiastolicPressure = 0;
        HeartRate = 0;
        Comment = "";
    }

    public static Measurement readFromParcel(Parcel in){
        String mID1 = in.readString();
        Date dateTime1 = null;
        try {
            dateTime1 = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").parse(in.readString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date dateTimeCreated1 = null;
        try {
            dateTimeCreated1 = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").parse(in.readString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Integer SystolicPressure1 = in.readInt();
        Integer DiastolicPressure1 = in.readInt();
        Integer HeartRate1 = in.readInt();
        String Comment1 = in.readString();
        return new Measurement(dateTime1, SystolicPressure1, DiastolicPressure1,
                HeartRate1, Comment1, dateTimeCreated1, mID1);
    }



    public Measurement(Date dateTime, Integer systolicPressure, Integer diastolicPressure,
                       Integer heartRate, String comment, Date dateTimeCreated, String mID) {
        this.dateTime = dateTime;
        SystolicPressure = systolicPressure;
        DiastolicPressure = diastolicPressure;
        HeartRate = heartRate;
        Comment = comment;
        this.dateTimeCreated = dateTimeCreated;
        this.mID = mID;
    }

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
        dest.writeString(this.dateTimeToString());
        dest.writeString(this.dateTimeToString());
        dest.writeInt(SystolicPressure);
        dest.writeInt(DiastolicPressure);
        dest.writeInt(HeartRate);
        dest.writeString(Comment);
    }

    private String dateTimeCreatedToString(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        //return dateFormat.format(dateTimeCreated);
        return "2018-09-09 12:00:00";
    }

    private String dateTimeToString(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        //return dateFormat.format(dateTime);
        return "2018-09-09 12:00:00";
    }

    public String getDateOnly(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        return "2018-09-09";
        //return dateFormat.format(dateTime);
    }

    public String getTimeOnly(){
        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        return "12:00:00";
        //return dateFormat.format(dateTime);
    }

}
