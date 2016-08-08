package com.neji.weatherpoc.weatherstats;

import com.google.gson.Gson;

import com.neji.weatherpoc.data.model.WeatherStatsModel;
import com.neji.weatherpoc.utils.WeatherTask;

import android.location.Location;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by nikhil.jadhav on 2/7/16.
 */
public class WeatherStatisticsPresenter implements WeatherStatisticsContract.Presenter, WeatherTask.TaskResults {
    private static String POSTFIX_URL = "&cnt=14&APPID=34469b05ae95bfd5d8186c537952b0a6";
    // Base address for informations download
    private final static String BASE_ADDR = "http://api.openweathermap.org/data/2.5/forecast/daily";
//    api.openweathermap.org/data/2.5/forecast/daily?lat=35&lon=139&cnt=10
    WeatherStatisticsFragment mStatisticsFragment = null;




    /*
	 * Mixing the String object containing the name of the city with the base
	 * address, we can generate the complete HTTP address to contact. Its format
	 * should be like this:
	 * http://api.openweathermap.org/data/2.5/weather?q=London This is the
	 * purpose of the getDataAddress method.
	 */
    private String getDataAddress(String city) {
        return BASE_ADDR + "?q=" + city+POSTFIX_URL;
    }


    private String getLatLangAPIAddress(Location loc){
        return BASE_ADDR +  "?lat="+loc.getLatitude()+"&lon="+loc.getLongitude()+POSTFIX_URL;
    }


    @Override
    public void start() {

    }

    protected void loadJSON(Location loc, final WeatherStatisticsFragment frg){

    }
    // This method performs the remote request and uses GSON to parse JSON data
    protected void loadJson(String selected, final WeatherStatisticsFragment frg) {
        this.mStatisticsFragment = frg;
        new WeatherTask(getDataAddress(selected), this).execute();

//        // We extend the AsyncTask class and instance an object directly.
//        new AsyncTask<String, Void, WeatherStatsModel>() {
//
//            // The doInBackground method contains the slower operations we want
//            // to perform on a separate thread
//            @Override
//            protected WeatherStatsModel doInBackground(String... params) {
//                // Params array contains only one object and its value is the
//                // string representing the name of the city
//                String selectedCity = params[0];
//
//                // URL object we'll use to connect to remote host
//                URL url;
//                try {
//                    // URL object is created. The input parameter we pass is the
//                    // URL to contact
//                    url = new URL(getDataAddress(selectedCity));
//
//                    // After connection, url provides the stream to the remote
//                    // data. Reader object can be used to read them
//                    Reader dataInput = new InputStreamReader(url.openStream());
//
//                    // GSON needs a stream to read data. We pass two parameters
//                    // to fromJson method: the stream to read and the Data class
//                    // structure for automatic parsing
//                    WeatherStatsModel data = new Gson().fromJson(dataInput, WeatherStatsModel.class);
//                    return data;
//                } catch (MalformedURLException e1) {
//                    return null;
//                } catch (IOException e1) {
//                    return null;
//                }
//            }
//
//            // The onPostExecute method receives the return value of
//            // doInBackground. Remember that onPostExecute works on the main
//            // thread of the application
//            protected void onPostExecute(WeatherStatsModel result) {
//                if (result != null) {
//                    frg.loadAdapter(result);
//                }
//            };
//
//            // The execute method is invoked to activate the background
//            // operation
//        }.execute(selected);

    }

    @Override
    public void loadResponse(String string) {
        WeatherStatsModel data = new Gson().fromJson(string, WeatherStatsModel.class);
        if (data!= null) {
            mStatisticsFragment.loadAdapter(data);
        }
    }
}
