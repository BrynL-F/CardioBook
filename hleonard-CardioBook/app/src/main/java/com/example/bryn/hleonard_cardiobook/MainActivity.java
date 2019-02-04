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

/**
 * MainActivity controls the main screen. Its goal is to display a list of old measurements with
 * relevant information, and provide the implementation for adding a new or editing an old measurement.
 * To ensure that the app stores data when closed and when a new activity is launched,
 * MainActivity writes to and pulls from a file. It uses a recycler view to display the list.
 * When navigating to a new activity (the measurement activity), the main activity provides a
 * measurement in the form of a parcel either through the recyclerview or on a button press.
 * a
 */

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String FILENAME = "file1.sav";
    private ArrayList<Measurement> measurements = new ArrayList<>();
    private RecyclerViewAdapter adapter;
    private RecyclerView oldMeasurementsList;
    static final int REQUEST_CODE = 1;


    /**
     * On create reloads the saved instance, and creates an onClickListener for the 'create new
     * measurement' button. It also initializes the recycler view and creates the apapter needed
     * to correctly display/store the measurements list.
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //toolbar setup
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        oldMeasurementsList = findViewById(R.id.recyclerview);


        //initializing recycler view to display and store the measurements list
        initRecyclerView();

        /**
         * Creates listener for the add measrurement button. When it is clicked, a new measurement
         * is created and added to the measurements list. This measurement is then passed into the
         * MeasurementActivity as a parcel; MainActivity is waiting for an incoming parcel
         */
        ImageButton addMeasurementButton = findViewById(R.id.add);
        addMeasurementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MeasurementActivity.class);
                Measurement newMeasurement = new Measurement();
                measurements.add(newMeasurement);
                intent.putExtra("measurementParcel", newMeasurement);
                intent.putExtra("IsNewMeasurement", true);
                saveInFile();
                startActivityForResult(intent, REQUEST_CODE);
            }

        });

        //getting incoming parcels through intent
        getIncomingIntents();


    }

    /**
     * When the add starts, load old measurements and initialize the recyclerview.
     */

    @Override
    protected void onStart() {
        super.onStart();
        loadMeasurementsFromFile();
        initRecyclerView();
        oldMeasurementsList.setAdapter(adapter);
    }

    /**
     * This method converts json file contents into a measurements list using GSON.
     * Note that this code is taken from the lab TA's example in lonelyTwitter.
     * @return measurements[], an array of measurements
     */
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


    /**
     * this method sets up/initializes the Recycler view used for the measurements list. It creates
     * a recycler adapter and sets the recycler view in content_main.xml with it.
     * Note that this recyclerview function as well as some of the RecyclerViewAdapter.java is
     * based off of CodingWithMitch's video RecyclerView
     * (https://www.youtube.com/watch?v=Vyqz_-sJGFk) and RecyclerView onClickListener to
     * New Activity (https://www.youtube.com/watch?v=ZXoGG2XTjzU)
     */

    private void initRecyclerView(){

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        adapter = new RecyclerViewAdapter(this, measurements);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.notifyDataSetChanged();
    }


    /**
     * This method catches the incoming Measurement parcels that are sent via intents.
     * It then checks to see if the incoming measurement is in the measurements list and updates it
     * in the measurements list and the file. It then reloads the measurements from the file.
     */
    protected void getIncomingIntents(){
        loadMeasurementsFromFile();

        Bundle b = getIntent().getExtras();
        if(b!=null){
            Measurement measurement = b.getParcelable("measurementParcel");

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

    /**
     * This method is based on the TA's lonelyTwitter app. This method takes the measurements list
     * and streams it into the prespecified file using GSON.
     */
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

