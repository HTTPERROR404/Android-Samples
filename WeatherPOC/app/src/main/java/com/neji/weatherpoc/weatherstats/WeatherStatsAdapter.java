package com.neji.weatherpoc.weatherstats;


import com.neji.weatherpoc.R;
import com.neji.weatherpoc.data.model.List;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by nikhil.jadhav on 9/7/16.
 */
public class WeatherStatsAdapter extends RecyclerView.Adapter<WeatherStatsAdapter.BindingHolder> {
    private ArrayList<List> mDataset;
    private final static String ICON_ADDR = "http://openweathermap.org/img/w/";
    Context mContext = null;
    // Provide a suitable constructor (depends on the kind of dataset)
    public WeatherStatsAdapter(ArrayList<List> myDataset, Context context) {
        mDataset = myDataset;mContext = context;
    }



    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class BindingHolder extends RecyclerView.ViewHolder {
        public View mTextView;
        TextView city, desc, tempmin, tempmax;
        ImageView img;
        public BindingHolder(View v) {
            super(v);
            mTextView = v;
            city = (TextView) mTextView.findViewById(R.id.city);
            desc = (TextView) mTextView.findViewById(R.id.description);
            tempmax = (TextView) mTextView.findViewById(R.id.maxTemp);
            tempmin = (TextView) mTextView.findViewById(R.id.minTemp);
            img = (ImageView) mTextView.findViewById(R.id.img);
        }

    }

    // Create new views (invoked by the layout manager)
    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weather_stats_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters

        BindingHolder vh = new BindingHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.city.setText(mDataset.get(position).getDt());
        holder.desc.setText(mDataset.get(position).getWeather().get(0).getDescription());
        holder.tempmin.setText(mDataset.get(position).getTemp().getMin() + "");
        holder.tempmax.setText(mDataset.get(position).getTemp().getMax() + "");
        Picasso.with(mContext).load(ICON_ADDR + mDataset.get(position).getWeather().get(0).getIcon() + ".png").into(holder.img);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
