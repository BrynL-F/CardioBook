package com.example.bryn.hleonard_cardiobook;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DialogTitle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Date;

public class MeasurementActivity extends AppCompatActivity {
    private Measurement measurement;


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

                Intent intent = new Intent(MeasurementActivity.this, MainActivity.class);
                intent.putExtra("measurementParcel", measurement);
                MeasurementActivity.this.startActivity( intent);
                finish();
            }
    });
    }

    private void getIncomingIntents(){

        Bundle b = getIntent().getExtras();
        if(b!=null){
            measurement = (Measurement) b.getParcelable("measurementParcel");
            if(!b.getBoolean("IsNewMeasurement")){
                fillFields();
            }
            else{
                EditText comment = findViewById(R.id.comment_value);
                comment.setText("New!");
            }
        }
    }

    private void fillFields(){
        //date = measurement.getDateTime();
        //time = measurement.getDateTime();
        TextView date = findViewById(R.id.date_value);
        TextView time = findViewById(R.id.time_value);
        EditText systolic = findViewById(R.id.systolic_value);
        EditText diastolic = findViewById(R.id.diastolic_value);
        EditText heartrate = findViewById(R.id.heart_rate_value);
        EditText comment = findViewById(R.id.comment_value);
        date.setText(measurement.getDateOnly());
        time.setText(measurement.getTimeOnly());
        systolic.setText(Integer.toString(measurement.getSystolicPressure()));
        diastolic.setText(Integer.toString(measurement.getDiastolicPressure()));
        heartrate.setText(Integer.toString(measurement.getHeartRate()));
        comment.setText(measurement.getComment());

    }

}
