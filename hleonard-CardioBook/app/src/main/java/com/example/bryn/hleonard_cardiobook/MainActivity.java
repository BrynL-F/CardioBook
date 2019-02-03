package com.example.bryn.hleonard_cardiobook;

import android.content.Intent;
import android.icu.util.Measure;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String FILENAME = "file.sav";
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<Measurement> measurements = new ArrayList<>();
    RecyclerViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageButton addMeasurementButton = (ImageButton) findViewById(R.id.add);
        addMeasurementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MeasurementActivity.class);
                Measurement newMeasurement = new Measurement();
                measurements.add(newMeasurement);
                Toast.makeText(MainActivity.this, "New measurement created", Toast.LENGTH_SHORT).show();
                intent.putExtra("measurementParcel", newMeasurement);
                intent.putExtra("IsNewMeasurement", true);
                startActivity(intent);
            }

        });

        initsome();

    }

    @Override
    protected void onStart() {
        super.onStart();
        loadMeasurementsFromFile();
    }


    //Used TA code here
    private Measurement[] loadMeasurementsFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Measurement>>() {}.getType();
            measurements = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            measurements = new ArrayList<Measurement>();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return measurements.toArray(new Measurement[measurements.size()]);
    }
    



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initsome(){
        names.add("test 56");
        names.add("test 2");
        names.add("yeye");
        initRecyclerView();
    }
    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        adapter = new RecyclerViewAdapter(this, names);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getIncomingIntents(){

        Bundle b = getIntent().getExtras();
        if(b!=null){
            Measurement measurement = (Measurement) b.getParcelable("measurementParcel");

        }
    }
}
