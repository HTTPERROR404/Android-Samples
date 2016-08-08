package com.neji.weatherpoc.cityselection;

import com.neji.weatherpoc.BasePresenter;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import java.util.ArrayList;

/**
 * Created by nikhil.jadhav on 2/7/16.
 */
public class CitySelectionPresenter implements BasePresenter, LocationListener{

    ArrayList<String> mCityList = new ArrayList();


    @Override
    public void start() {
    }



    public void performBtnAddCityClick(String cityName){
        int prevSize = mCityList.size();
        if(cityName.length()>0) {
            if (mCityList.indexOf(cityName) == -1) {
                mCityList.add(cityName);
            }
        }
        if(prevSize == 0 && mCityList.size()>0){

        }
    }


    public String[] getCityArray(){
        return mCityList.toArray(new String[1]);
    }


    @Override
    public void onLocationChanged(Location location) {
        
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
