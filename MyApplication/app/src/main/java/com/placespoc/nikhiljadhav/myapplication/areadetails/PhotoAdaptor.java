package com.placespoc.nikhiljadhav.myapplication.areadetails;

import com.placespoc.nikhiljadhav.myapplication.R;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by nikhil.jadhav on 6/8/16.
 */
public class PhotoAdaptor extends RecyclerView.Adapter<PhotoAdaptor.BindingHolder> {
    private AreaDetailsFragment mContext = null;
    private final ArrayList<AreaDetailsPresentor.PhotoTask.AttributedPhoto> mDataset;
    private final AreaDetailsPresentor mPresentor;


    public PhotoAdaptor(AreaDetailsFragment view, ArrayList<AreaDetailsPresentor.PhotoTask.AttributedPhoto> dataset, AreaDetailsPresentor areaDetailsPresentor) {
        mContext = view;
        mDataset = dataset;
        mPresentor = areaDetailsPresentor;
    }

    // Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder
    public static class BindingHolder extends RecyclerView.ViewHolder {
        public final View mTextView;
        final ImageView img;

        public BindingHolder(View v) {
            super(v);
            mTextView = v;
            img = (ImageView) mTextView.findViewById(R.id.ivPhoto);
        }

    }

    // Create new views (invoked by the layout manager)
    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent,
                                            int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.photo_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters

        return new BindingHolder(v);

    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(BindingHolder holder, final int position) {
        holder.img.setImageBitmap(mDataset.get(holder.getAdapterPosition()).bitmap);
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresentor.handleItemClick(position);
            }
        });
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }



}