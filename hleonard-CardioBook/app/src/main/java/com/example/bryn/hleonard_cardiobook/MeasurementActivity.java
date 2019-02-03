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
import android.widget.ImageButton;
import android.widget.TextView;

public class MeasurementActivity extends AppCompatActivity {

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
                finish();
            }
    });
    }

    private void getIncomingIntents(){
        
        //checking for extras
        if(getIntent().hasExtra("name")){
            String name = getIntent().getStringExtra("name");
            setName(name);
        }
    }

    private void setName(String name){
        TextView comment = findViewById(R.id.comment_value);
        comment.setText(name);
    }
}
