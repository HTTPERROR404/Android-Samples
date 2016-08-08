package com.placespoc.nikhiljadhav.myapplication.placeselection;

import com.placespoc.nikhiljadhav.myapplication.BaseActivity;
import com.placespoc.nikhiljadhav.myapplication.R;

import android.os.Bundle;

public class PlaceSelectionActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PlaceSelectionFragment addEditTaskFragment =
                (PlaceSelectionFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (addEditTaskFragment == null) {
            loadFragment(PlaceSelectionFragment.newInstance());
        }
    }

}
