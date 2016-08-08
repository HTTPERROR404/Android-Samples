package com.neji.weatherpoc;

import com.neji.weatherpoc.R;
import com.neji.weatherpoc.cityselection.CitySelectionFragment;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadFragment(new CitySelectionFragment());
    }

    private void loadFragment(Fragment frg){
        getSupportFragmentManager().beginTransaction().replace(R.id.frgMain, frg).addToBackStack(null).commit();
    }
}
