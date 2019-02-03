package com.example.bryn.hleonard_cardiobook;

import java.util.Date;
import java.util.UUID;

public class Measurement {
    private Date dateTime;
    private Integer SystolicPressure;
    private Integer DiastolicPressure;
    private Integer HeartRate;
    private String Comment;
    private Date dateTimeCreated;
    private String mID;



    public Measurement(Date datetime, Integer systolicPressure, Integer diastolicPressure, Integer heartRate) {
        mID = UUID.randomUUID().toString();
        dateTimeCreated = new Date();
        dateTime = datetime;
        SystolicPressure = systolicPressure;
        DiastolicPressure = diastolicPressure;
        HeartRate = heartRate;
        Comment = "";
    }

    public Measurement(Date datetime, Integer systolicPressure, Integer diastolicPressure, Integer heartRate, String comment) {
        mID = UUID.randomUUID().toString();
        dateTimeCreated = new Date();
        dateTime = datetime;
        SystolicPressure = systolicPressure;
        DiastolicPressure = diastolicPressure;
        HeartRate = heartRate;
        Comment = comment;
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

}
