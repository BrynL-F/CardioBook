package com.example.bryn.hleonard_cardiobook;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DialogTitle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MeasurementActivity extends AppCompatActivity {
    private Measurement measurement;
    Context mContext = this;
    TextView date;
    TextView time;
    EditText systolic;
    EditText diastolic;
    EditText heartRate;
    EditText comment;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurement);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(myToolbar);

        getIncomingIntents();
        getSupportActionBar().setTitle("CardioBook Measurement");

        Button saveMeasurementButton = (Button) findViewById(R.id.save_measurement);
        saveMeasurementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkMeasurementFields()) {
                    updateMeasurementFields();
                    Intent intent = new Intent(mContext, MainActivity.class);
                    intent.putExtra("measurementParcel", measurement);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(mContext, "Input fields are incorrect. Please retry.", Toast.LENGTH_LONG).show();
                }
            }
    });


        final Button dateButton = findViewById(R.id.date_button);
        dateButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Calendar datecalendar = Calendar.getInstance();
                Date date1 = null;
                try {
                    date1 = new SimpleDateFormat("yyyy-MM-dd").parse(measurement.getDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Toast.makeText(mContext, date1.toString(), Toast.LENGTH_SHORT).show();
                datecalendar.setTime(date1);
                int day = datecalendar.get(Calendar.DAY_OF_MONTH);
                int month = datecalendar.get(Calendar.MONTH);
                int year = datecalendar.get(Calendar.YEAR);
                Toast.makeText(mContext, "TEH DATE IS " + measurement.getDate() , Toast.LENGTH_SHORT).show();
                Toast.makeText(mContext, "TEH DATE IS " + day + year , Toast.LENGTH_SHORT).show();
                DatePickerDialog datepicker = new DatePickerDialog( mContext, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker datepicker, int mYear, int mMonth, int mDay){
                        dateButton.setText(mYear + "-" + (mMonth + 1) + "-" + mDay);
                    }

                }, year, month, day);

                datepicker.show();
            }

        });


        final Button timeButton = findViewById(R.id.time_button);
        timeButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Calendar datecalendar = Calendar.getInstance();
                Date date1 = null;

                try {
                    date1 = new SimpleDateFormat("hh:mm:ss").parse(measurement.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                datecalendar.setTime(date1);

                int hour = datecalendar.get(Calendar.HOUR_OF_DAY);
                int minute = datecalendar.get(Calendar.MINUTE);

                TimePickerDialog timepicker = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker timePicker, int mhour, int mminute) {
                        Button sTime = findViewById(R.id.time_button);
                        sTime.setText(  mhour + ":" + String.format("%02d", mminute) + ":00");
                    }
                },hour, minute, false);
                timepicker.show();
            }

        });

    }

    private void getIncomingIntents(){

        Bundle b = getIntent().getExtras();
        if(b!=null){
            measurement = b.getParcelable("measurementParcel");
            if(!b.getBoolean("IsNewMeasurement")){
                initMeasurementFields();
            }
            else{
                initMeasurementFields();
            }
        }
    }

    private void initMeasurementFields(){
        date = findViewById(R.id.date_button);
        time = findViewById(R.id.time_button);
        systolic = findViewById(R.id.systolic_value);
        diastolic = findViewById(R.id.diastolic_value);
        heartRate = findViewById(R.id.heart_rate_value);
        comment = findViewById(R.id.comment_value);

        date.setText(measurement.getDate());
        time.setText(measurement.getTime());
        systolic.setText(Integer.toString(measurement.getSystolicPressure()));
        diastolic.setText(Integer.toString(measurement.getDiastolicPressure()));
        heartRate.setText(Integer.toString(measurement.getHeartRate()));
        comment.setText(measurement.getComment());

    }

    private void updateMeasurementFields(){
        measurement.setDate(date.getText().toString());
        measurement.setTime(time.getText().toString());
        measurement.setSystolicPressure(Integer.parseInt(systolic.getText().toString()));
        measurement.setDiastolicPressure(Integer.parseInt(diastolic.getText().toString()));
        measurement.setHeartRate(Integer.parseInt(heartRate.getText().toString()));
        measurement.setComment(comment.getText().toString());

    }

    private boolean checkMeasurementFields(){

        if (systolic.getText()==null || diastolic.getText()==null || heartRate.getText() ==null ||
                date.getText()==null || time.getText()==null){
            return false;
        }
        else if (!isPositiveInteger(systolic.getText()) || !isPositiveInteger(diastolic.getText()) ||
                !isPositiveInteger(heartRate.getText())){
            return false;
        }
        else if(comment.getText().length()>20){
            return false;
        }
        else{
            return true;
        }

    }


    private boolean isPositiveInteger(Editable editText){
        try {
            int integer = Integer.parseInt(editText.toString());
            if (integer <1){
                return false;
            }

        } catch (NumberFormatException e) {
            Log.i("",e+" is not a number");
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //do nothing
    }


}
