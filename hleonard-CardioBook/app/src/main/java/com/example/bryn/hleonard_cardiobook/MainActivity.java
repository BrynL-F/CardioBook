package com.example.bryn.hleonard_cardiobook;

import android.content.Intent;
import android.icu.util.Measure;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String FILENAME = "file1.sav";
    private ArrayList<Measurement> measurements = new ArrayList<>();
    private RecyclerViewAdapter adapter;
    private RecyclerView oldMeasurementsList;
    static final int REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        oldMeasurementsList = findViewById(R.id.recyclerview);

        ImageButton addMeasurementButton = findViewById(R.id.add);

        initRecyclerView();

        addMeasurementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MeasurementActivity.class);
                Measurement newMeasurement = new Measurement();
                Toast.makeText(MainActivity.this, "New measurement created:" + newMeasurement.getmID(), Toast.LENGTH_LONG).show();
                measurements.add(newMeasurement);
                intent.putExtra("measurementParcel", newMeasurement);
                intent.putExtra("IsNewMeasurement", true);
                saveInFile();
                startActivityForResult(intent, REQUEST_CODE);
            }

        });

        getIncomingIntents();


    }

    @Override
    protected void onStart() {
        super.onStart();
        loadMeasurementsFromFile();
        initRecyclerView();
        oldMeasurementsList.setAdapter(adapter);
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



    /*
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
    */


    private void initRecyclerView(){

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        adapter = new RecyclerViewAdapter(this, measurements);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.notifyDataSetChanged();
    }


    protected void getIncomingIntents(){
        loadMeasurementsFromFile();

        Bundle b = getIntent().getExtras();
        if(b!=null){
            Measurement measurement = b.getParcelable("measurementParcel");

            Toast.makeText(MainActivity.this, "Systolli:" + Integer.toString(measurement.getSystolicPressure()),
                    Toast.LENGTH_LONG).show();

            for(Measurement m: measurements){

                if (m.getmID().equals(measurement.getmID())){
                    measurements.set(measurements.indexOf(m), measurement);
                    adapter.notifyDataSetChanged();
                    saveInFile();
                    loadMeasurementsFromFile();
                }
                else{
                    Log.d(TAG, "getIncomingIntents: no matching measurement");
                }
            }

        }
    }

    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME, 0);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(measurements, writer);
            writer.flush();
            fos.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

