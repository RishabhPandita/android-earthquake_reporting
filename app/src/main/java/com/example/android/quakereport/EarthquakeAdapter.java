package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.resource;
import static com.example.android.quakereport.R.id.quake_list;

/**
 * Created by Rishabh on 16-05-2017.
 */

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {


    public EarthquakeAdapter(Context context, List<Earthquake> quakeList) {
        super(context, 0, quakeList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_view, parent, false);
        }

        Earthquake eq = getItem(position);


        TextView mag_adap = (TextView) listItemView.findViewById(R.id.mag_text);
        Double mag_double = eq.getMagnitude();
        DecimalFormat mag_format = new DecimalFormat("0.0");
        String magnitude = mag_format.format(mag_double);
        mag_adap.setText(magnitude);

        GradientDrawable mag_circle = (GradientDrawable)mag_adap.getBackground();
        int mag_color = getMagColor(mag_double);
        mag_circle.setColor(mag_color);




        String primaryLoc,secondaryLoc,loc = eq.getLocation(),split[];
        if(loc.contains("of")) {
            split=loc.split("of");
            primaryLoc = split[1];
            secondaryLoc = split[0] + getContext().getString(R.string.of);
        }
        else{
            primaryLoc = loc;
            secondaryLoc = getContext().getString(R.string.near_the);
        }
        TextView p_loc_adap = (TextView) listItemView.findViewById(R.id.primary_location_text);
        p_loc_adap.setText(primaryLoc);
        TextView s_loc_adap = (TextView) listItemView.findViewById(R.id.secondary_location_text);
        s_loc_adap.setText(secondaryLoc);




        TextView date_adap = (TextView) listItemView.findViewById(R.id.date_text);
        date_adap.setText(eq.getDate());

        TextView time_adap = (TextView) listItemView.findViewById(R.id.time_text);
        time_adap.setText(eq.getTime());

        return listItemView;
    }

    private int getMagColor(Double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }
}
