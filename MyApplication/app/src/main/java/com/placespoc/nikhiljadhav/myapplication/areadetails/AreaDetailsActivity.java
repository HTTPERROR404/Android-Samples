package com.placespoc.nikhiljadhav.myapplication.areadetails;

import com.placespoc.nikhiljadhav.myapplication.BaseActivity;
import com.placespoc.nikhiljadhav.myapplication.R;

import android.os.Bundle;

public class AreaDetailsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AreaDetailsFragment addEditTaskFragment =
                (AreaDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        String placeId = getIntent().getStringExtra("placeID");
        if (addEditTaskFragment == null) {
            AreaDetailsFragment frg =AreaDetailsFragment.newInstance();
            frg.setID(placeId);
            loadFragment(frg);
        }
    }

}
