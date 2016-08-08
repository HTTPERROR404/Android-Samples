
package com.neji.weatherpoc.data.model;

import java.util.ArrayList;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class WeatherStatsModel {

    @SerializedName("city")
    @Expose
    private City city;
    @SerializedName("list")
    @Expose
    private java.util.List<com.neji.weatherpoc.data.model.List> list = new ArrayList<com.neji.weatherpoc.data.model.List>();

    /**
     * 
     * @return
     *     The city
     */
    public City getCity() {
        return city;
    }

    /**
     * 
     * @param city
     *     The city
     */
    public void setCity(City city) {
        this.city = city;
    }

    /**
     * 
     * @return
     *     The list
     */
    public java.util.List<com.neji.weatherpoc.data.model.List> getList() {
        return list;
    }

    /**
     * 
     * @param list
     *     The list
     */
    public void setList(java.util.List<com.neji.weatherpoc.data.model.List> list) {
        this.list = list;
    }

}
