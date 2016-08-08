package com.neji.weatherpoc.utils;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

/**
 * Created by nikhil.jadhav on 2/7/16.
 */
public class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location loc) {
        }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
        public void onProviderDisabled(String provider) {}

        @Override
        public void onProviderEnabled(String provider) {}

       

}
