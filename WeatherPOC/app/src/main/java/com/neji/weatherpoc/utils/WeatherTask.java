package com.neji.weatherpoc.utils;

import com.google.gson.Gson;

import com.neji.weatherpoc.data.model.Weather;
import com.neji.weatherpoc.data.model.WeatherStatsModel;

import org.json.JSONException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by nikhil.jadhav on 2/7/16.
 */
public class WeatherTask extends AsyncTask<String, Void, String> {
    private String mURL;
    private TaskResults taskresult = null;

    public WeatherTask(String url, TaskResults results) {
        this.mURL = url;
        this.taskresult = results;
    }

    // The doInBackground method contains the slower operations we want
    // to perform on a separate thread
    @Override
    protected String doInBackground(String... params) {
        URL url;
        try {
            url = new URL(mURL);

            BufferedReader r = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line).append('\n');
            }
            return total.toString();
        } catch (MalformedURLException e1) {
            return null;
        } catch (IOException e1) {
            return null;
        }
    }

    // The onPostExecute method receives the return value of
    // doInBackground. Remember that onPostExecute works on the main
    // thread of the application
    protected void onPostExecute(String result) {
        taskresult.loadResponse(result);
    }

    public interface TaskResults{
        void loadResponse(String string);
    }

}

