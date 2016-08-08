package com.placespoc.nikhiljadhav.myapplication.placeselection;

import com.placespoc.nikhiljadhav.myapplication.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by nikhil.jadhav on 4/8/16.
 */
public class PlaceSelectionFragment extends Fragment implements PlaceSelectionContract.PlaceSelectionView{

    private PlaceSelectionPresentor mPresentor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_place_selection, container, false);
        mPresentor = new PlaceSelectionPresentor();
        mPresentor.setView(this);
        Button btnSearch = (Button) root.findViewById(R.id.searchCity);
        btnSearch.setOnClickListener(mPresentor);
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresentor.handleactivityResult(requestCode,resultCode, data);
    }

    public static PlaceSelectionFragment newInstance() {
        return new PlaceSelectionFragment ();
    }

    public PlaceSelectionFragment(){
    }
}
