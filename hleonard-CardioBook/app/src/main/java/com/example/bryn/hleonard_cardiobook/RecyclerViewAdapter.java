package com.example.bryn.hleonard_cardiobook;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * RecyclerView Adapter is an adapter for the recycler view. This is used to display the elements of
 * the measurement list correctly. The elements of the recycler view are set to values from their
 * measurements and a warning icon is displayed when cardiac values are out of bound. This
 * class also handles opening an old measurement when clicked on, as well as deleting elements off
 * the list.
 *
 * Note that this was made with help from the CodingWithMitch tutorial videos at
 * https://www.youtube.com/watch?v=ZXoGG2XTjzU and https://www.youtube.com/watch?v=Vyqz_-sJGFk which
 * were published Jan1, 2018 and Jan2, 2018 respectively.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<Measurement> measurements = new ArrayList<>();
    private Context mContext;
    private static String FILENAME;


    public RecyclerViewAdapter(Context mContext, ArrayList<Measurement> measurements, String filename) {
        this.FILENAME = filename;
        this.measurements = measurements;
        this.mContext = mContext;
    }

    /**
     * Creates the viewholder and fills the view to the size specified in its xml.
     * @param viewGroup
     * @param i
     * @return
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_list_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    /**
     * This method isplays the desired fields in each list element display and takes action when
     * an item is clicked on.
     * @param viewHolder
     * @param i
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Log.d(TAG, "onBindViewHolder: is called");
        viewHolder.date_display.setText(measurements.get(i).getDate() + " " + measurements.get(i).getTime());
        viewHolder.systolic_display.setText(Integer.toString(measurements.get(i).getSystolicPressure()));
        viewHolder.diastolic_display.setText(Integer.toString(measurements.get(i).getDiastolicPressure()));
        viewHolder.heart_rate_display.setText(Integer.toString(measurements.get(i).getHeartRate()));


        //warning flag image for heart values out of bounds
        viewHolder.warning.setVisibility(HeartValuesOutOfBound(measurements.get(i)));

        /**
         * Used to transition to a new activity when an item is clicked on, this method
         * creates an intent and pushes the measurement parcel to the new activity.
         */
        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on" + measurements.get(i).getTime());

                Intent intent = new Intent(mContext, MeasurementActivity.class);
                intent.putExtra("measurementParcel", measurements.get(i));
                intent.putExtra("isNewMeasurement", false);
                mContext.startActivity(intent);

            }
        });

        /**
         * For deleting measurement
         */
        viewHolder.delete_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                measurements.remove(i);
                notifyDataSetChanged();
                saveInFile();
            }
        });



    }

    /**
     * Gets size of list
     * @return number of measurements in list
     */
    @Override
    public int getItemCount() {
        return measurements.size();
    }

    /**
     * Inner class for ViewHolder class. Here, this matches the display values to their element in
     * layout_list_item.xml
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView date_display;
        ConstraintLayout parentLayout;
        TextView systolic_display;
        TextView diastolic_display;
        TextView heart_rate_display;
        ImageButton delete_button;
        ImageView warning;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date_display = itemView.findViewById(R.id.date_display);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            systolic_display = itemView.findViewById(R.id.systolic_display);
            diastolic_display = itemView.findViewById(R.id.diastolic_display);
            heart_rate_display = itemView.findViewById(R.id.heart_rate_display);
            delete_button = itemView.findViewById(R.id.delete);
            warning = itemView.findViewById(R.id.warning);


        }


    }

    /**
     * This method checks the cardiac measurement values and returns a visibilty int to either
     * hide or show the warning image on each measurement item.
     * @param m
     * @return Visible or Gone int
     */
    public Integer HeartValuesOutOfBound(Measurement m) {

        if (m.getSystolicPressure() > 140 || m.getSystolicPressure() < 90) {
            Log.d(TAG, "HeartValuesOutOfBound: VALUE OF 2");
            return View.VISIBLE;
        } else if (m.getDiastolicPressure() > 90 || m.getDiastolicPressure() < 60) {
            return View.VISIBLE;
        } else {
            return View.GONE;
        }

    }


    /**
     * This method is based on the TA's lonelyTwitter app. This method takes the measurements list
     * and streams it into the prespecified file using GSON.
     */
    private void saveInFile() {
        try {
            FileOutputStream fos = mContext.openFileOutput(FILENAME, 0);
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

