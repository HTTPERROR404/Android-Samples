package com.placespoc.nikhiljadhav.myapplication;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by nikhil.jadhav on 5/8/16.
 */
public class BaseActivity extends AppCompatActivity {

    protected void loadFragment(Fragment frg){
        getSupportFragmentManager().beginTransaction().replace(R.id.contentFrame, frg).addToBackStack(frg.getClass().getName()).commit();
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }
}
