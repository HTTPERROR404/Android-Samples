package com.neji.weatherpoc;

import com.google.gson.Gson;

import com.neji.weatherpoc.data.model.City;
import com.neji.weatherpoc.data.model.WeatherModel;
import com.neji.weatherpoc.utils.WeatherTask;
import com.neji.weatherpoc.weatherstats.WeatherStatisticsFragment;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class StatsListActivity extends ListFragment implements WeatherTask.TaskResults {
	// Base address for informations download
	private final static String BASE_ADDR = "http://api.openweathermap.org/data/2.5/weather";
    private static String POSTFIX_URL = "&APPID=34469b05ae95bfd5d8186c537952b0a6";

	// The following array contains the names of the cities we are interested in
	private ArrayList<City> cities = new ArrayList<>();/* { "Tokyo", "London", "Moscow",
			"Ottawa", "Madrid", "Lisboa", "Zurich" };*/

	// Reference to the Adapter object. WeatherAdapter is a custom class,
	// defined in a separate file

	private WeatherAdapter adapter;
    private ArrayList<WeatherModel> mModelList = null;

    public void setCities(ArrayList<City> tmp){
        cities = tmp;
    }

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModelList = new ArrayList<>();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
loadNextFragment(mModelList.get(position).getName());
        super.onListItemClick(l, v, position, id);
    }

    private void loadNextFragment(String city){
        WeatherStatisticsFragment fragment = new WeatherStatisticsFragment();
        fragment.setCity(city);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frgMain, fragment).addToBackStack(null).commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (container != null) {
            container.removeAllViews();
        }

        // The adapter object is instantiated
        adapter = new WeatherAdapter(this.getContext());

        // The adapter is linked to the ListView
        setListAdapter(adapter);

        // We request informations separately via HTTP for every city in the
        // array
        for (City c : cities)
            loadJson(c.city);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    // This method performs the remote request and uses GSON to parse JSON data
	protected void loadJson(String selected) {
        new WeatherTask(getDataAddress(selected), this).execute();
	}

    @Override
    public void loadResponse(String string) {
        if (string != null) {
            WeatherModel data = new Gson().fromJson(string, WeatherModel.class);
                    mModelList.add(data);
					adapter.add(data);
		}
    }
}
