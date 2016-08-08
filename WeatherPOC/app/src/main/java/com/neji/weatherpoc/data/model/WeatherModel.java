
package com.neji.weatherpoc.data.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeatherModel {

    @SerializedName("weather")
    @Expose
    private List<Weather> weather = new ArrayList<Weather>();
    @SerializedName("main")
    @Expose
    private Temperature main;
    @SerializedName("name")
    @Expose
    private String name;

    /**
     *
     * @return
     * The weather
     */
    public List<Weather> getWeather() {
        return weather;
    }

    /**
     *
     * @param weather
     * The weather
     */
    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    /**
     *
     * @return
     * The main
     */
    public Temperature getMain() {
        return main;
    }

    /**
     *
     * @param main
     * The main
     */
    public void setMain(Temperature main) {
        this.main = main;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

}
