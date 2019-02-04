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

/**
 * Measurement class represents a measurement (old or new). It implements the interface Parcelable
 * in order to transfer/send it across activiities. When initially created, the date and time is
 * sent to the current date, while a unique identifier is also created. All other fields are
 * provided through the setters.
 * This class includes an inner class in order to satisfy the parcelable interface. The methods
 * readFromParcel and writeToParcel are also implemented heere to satisfy interface requirements.
 */
public class Measurement implements Parcelable {
    private String date;
    private String time;
    private Integer SystolicPressure;
    private Integer DiastolicPressure;
    private Integer HeartRate;
    private String Comment;
    private String mID = UUID.randomUUID().toString();


    /**
     * Consructor for parcel sharing
     */
    public Measurement(Parcel in) throws ParseException {
        mID = in.readString();
        date = in.readString();
        SystolicPressure = in.readInt();
        DiastolicPressure = in.readInt();
        HeartRate = in.readInt();
        Comment = in.readString();
    }

    /**
     * Basic constructor
     */
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


    /**
     * readFromParcel collects the parcel information and returns the converted Measurement.
     * @param in
     * @return
     */
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




    /**
     * Constructor used for readFromParcel method
     * @param date
     * @param time
     * @param systolicPressure
     * @param diastolicPressure
     * @param heartRate
     * @param comment
     * @param mID
     */
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


    /**
     * Getter for the dTate field
     * @return date
     */
    public String getDate(){ return this.date; }

    /**
     * Setter for the date
     * @param dateString
     */
    public void setDate(String dateString) {
        this.date = dateString;
    }

    /**
     * Getter for the Time field
     * @return time
     */
    public String getTime(){ return this.time; }

    /**
     * Setter for the Time field
     * @param timeString
     */
    public void setTime(String timeString) {
        this.time = timeString;
    }

    /**
     * Getter for the SystolicPressure fjeld
     * @return SystolicPressure
     */

    public Integer getSystolicPressure() {
        return SystolicPressure;
    }

    /**
     * Setter for the SystolicPressure field
     * @param systolicPressure
     */
    public void setSystolicPressure(Integer systolicPressure) {
        SystolicPressure = systolicPressure;
    }

    /**
     * Getter for the DiastolicPressure field
     * @return Diasolic Pressure
     */

    public Integer getDiastolicPressure() {
        return DiastolicPressure;
    }

    /**
     * Setter for the DiasolicPressure field
     * @param diastolicPressure
     */
    public void setDiastolicPressure(Integer diastolicPressure) {
        DiastolicPressure = diastolicPressure;
    }

    /**
     * Getter for the HeartRate field
     * @return HeartRate
     */
    public Integer getHeartRate() {
        return HeartRate;
    }

    /**
     * Setter for the HeartRate feild
     * @param heartRate
     */
    public void setHeartRate(Integer heartRate) {
        HeartRate = heartRate;
    }

    /**
     * Getter for the Comment field
     * @return Comment
     */
    public String getComment() {
        return Comment;
    }

    /**
     * Setter for the Comment filed
     * @param comment
     */
    public void setComment(String comment) {
        Comment = comment;
    }

    /**
     * Getter for mID field
     * @return mID
     */
    public String getmID() {
        return mID;
    }


    /**
     * Necessary for Parceable implementation
     * @return size
     */
    @Override
    public int describeContents() {
        return 0;
    }


    /**
     * Inner class that creates a pareceable parcel for the measurement
     */
    public static final Parcelable.Creator<Measurement> CREATOR = new Parcelable.Creator<Measurement>() {

        /**
         * Creates a measurement from the parcel
         * @param in
         * @return measurement from parcel
         */
        public Measurement createFromParcel(Parcel in) {
            return readFromParcel(in);
        }

        /**
         * Creates an array of Masurements of a certain size
         * @param size
         * @return the array of measurements
         */
        public Measurement[] newArray(int size) {
            return new Measurement[size];
        }
    };


    /**
     * This method is part of the implementation of Parceable. It writese the Meaurement data fields
     * to the parcel
     * @param dest
     * @param flags
     */
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
