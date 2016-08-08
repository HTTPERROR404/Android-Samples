package com.neji.weatherpoc.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nikhil.jadhav on 8/7/16.
 */
public class City {
    public City(String city) {
        this.city = city;
    }

    @SerializedName("name")
    @Expose
    public String city;
}
