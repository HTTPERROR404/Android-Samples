
package com.neji.weatherpoc.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Temp {

    @SerializedName("min")
    @Expose
    private Double min;
    @SerializedName("max")
    @Expose
    private Double max;

    /**
     * 
     * @return
     *     The min
     */
    public Double getMin() {
        return min - 273.15;
    }

    /**
     * 
     * @param min
     *     The min
     */
    public void setMin(Double min) {
        this.min = min;
    }

    /**
     * 
     * @return
     *     The max
     */
    public Double getMax() {
        return max - 273.15;
    }

    /**
     * 
     * @param max
     *     The max
     */
    public void setMax(Double max) {
        this.max = max;
    }

}
