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

/**
 * The MeasurementActivity class manages the screen when opening a measurement. This includes when
 * adding a new measurement, or editing or viewing an old measurement. The measurement is passed
 * into the MeasurementActivity and passed back to the MainActivity using a parcel. This is managed
 * through getIncomingIntents and the save button. This class sets activity_measurement elements
 * from the Measurement parcel, and checks input on save. A datepicker and timepicker are used to
 * enhance the user experience as they select a date.
 */
public class MeasurementActivity extends AppCompatActivity {
    private Measurement measurement;
    private Context mContext = this;
    private TextView date;
    private TextView time;
    private EditText systolic;
    private EditText diastolic;
    private EditText heartRate;
    private EditText comment;

    /**
     * Sets up the activity for getting incoming parcles and the onClickListeners for the date,time,
     * and save buttons.
     * @param savedInstanceState
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurement);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(myToolbar);

        getIncomingIntents();
        getSupportActionBar().setTitle("CardioBook Measurement");

        /**
         * Sets the action for the save button click. If the field input is acceptable, it sends
         * back to main the updated measurement parcel.
         */
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
                    Toast.makeText(mContext, "Input fields are incorrect. Please retry.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        /**
         * This datepicker is a widget used to enhance user experience when selecting a date.
         * This pulls the date from the measurement object and sets the datepicker date to this date.
         * It sets the button text to the selected or default (pulled) date.
         * Note that this was based off of a datetimepicker from a joint personal project named
         * myTimes by myself and Ty Jamal.
         */
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
                int day = datecalendar.get(Calendar.DAY_OF_MONTH);
                int month = datecalendar.get(Calendar.MONTH);
                int year = datecalendar.get(Calendar.YEAR);

                DatePickerDialog datepicker = new DatePickerDialog( mContext,
                        new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker datepicker, int mYear, int mMonth, int mDay){
                        dateButton.setText(mYear + "-" + String.format("%02d",(mMonth + 1)) + "-" + mDay);
                    }

                }, year, month, day);

                datepicker.show();
            }

        });

        /**
         * This timeicker is a widget used to enhance user experience when selecting a date.
         * This pulls the time from the measurement object and sets the timepicker time to this time.
         * It sets the button text to the selected or default (pulled) time.
         * Note that this was based off of a datetimepicker from a joint personal project named
         * myTimes by myself and Ty Jamal.
         */
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

                TimePickerDialog timepicker = new TimePickerDialog(mContext,
                        new TimePickerDialog.OnTimeSetListener() {

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

    /**
     * This method catches the incoming Measurement parcels that are sent via intents.
     */
    private void getIncomingIntents(){

        Bundle b = getIntent().getExtras();
        if(b!=null){
            measurement = b.getParcelable("measurementParcel");
                initMeasurementFields();
        }

    }

    /**
     * This method takes the measurement fields and sets them to the elements in the
     * activity_measurement.xml file to display them.
     */
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

    /**
     * This method updates the measurement fields for measurement from the
     * elements in the activity_measurement.xml file
     */
    private void updateMeasurementFields(){
        measurement.setDate(date.getText().toString());
        measurement.setTime(time.getText().toString());
        measurement.setSystolicPressure(Integer.parseInt(systolic.getText().toString()));
        measurement.setDiastolicPressure(Integer.parseInt(diastolic.getText().toString()));
        measurement.setHeartRate(Integer.parseInt(heartRate.getText().toString()));
        measurement.setComment(comment.getText().toString());

    }


    /**
     * Checks to make sure that the input for the date, time, pressures, heartrate, and comment
     * fields are in the correct format.
     * @return boolean representing whether the input fields are correct
     */
    private boolean checkMeasurementFields(){

        //Pressures, date and heartRate are mandatory
        if (systolic.getText()==null || diastolic.getText()==null || heartRate.getText() ==null ||
                date.getText()==null || time.getText()==null){
            return false;
        }
        //pressures and heartrate must be positive integers
        else if (!isPositiveInteger(systolic.getText()) || !isPositiveInteger(diastolic.getText()) ||
                !isPositiveInteger(heartRate.getText())){
            return false;
        }
        //comment must be up to 20 characters.
        else if(comment.getText().length()>20){
            return false;
        }
        else{
            return true;
        }

    }

    /**
     * Takes in an EditText's value and determines whether it is a positive integer or not.
     * @param editText
     * @return boolean representing whether Edittable is positive integer
     */
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

    /**
     * Prevent user from escaping the measurement without saving
     */
    @Override
    public void onBackPressed() {
        //do nothing
    }


}
