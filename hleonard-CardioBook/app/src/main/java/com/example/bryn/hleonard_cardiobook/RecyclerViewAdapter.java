package com.example.bryn.hleonard_cardiobook;

import android.content.Context;
import android.content.Intent;
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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<Measurement> measurements = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter(Context mContext, ArrayList<Measurement> measurements) {
        this.measurements = measurements;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_list_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Log.d(TAG, "onBindViewHolder: is called");
        viewHolder.date_display.setText(measurements.get(i).getDate() + " " + measurements.get(i).getTime());
        viewHolder.systolic_display.setText(Integer.toString(measurements.get(i).getSystolicPressure()));
        viewHolder.diastolic_display.setText(Integer.toString(measurements.get(i).getDiastolicPressure()));
        viewHolder.heart_rate_display.setText(Integer.toString(measurements.get(i).getHeartRate()));

        ;
        viewHolder.warning.setVisibility(HeartValuesOutOfBound(measurements.get(i)));

        //for changing screens
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
    }

    @Override
    public int getItemCount() {
        return measurements.size();
    }

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
}

