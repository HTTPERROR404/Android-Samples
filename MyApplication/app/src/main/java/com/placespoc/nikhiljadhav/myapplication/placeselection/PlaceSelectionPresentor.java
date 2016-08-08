package com.placespoc.nikhiljadhav.myapplication.placeselection;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import com.placespoc.nikhiljadhav.myapplication.R;
import com.placespoc.nikhiljadhav.myapplication.areadetails.AreaDetailsActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

/**
 * Created by nikhil.jadhav on 4/8/16.
 */
public class PlaceSelectionPresentor implements PlaceSelectionContract.PlaceSelectionPresentor {

    private static final String TAG = "AreaDetailsPresentor";
    private Fragment mView = null;
    private final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private static final int PERMISSION_REQUEST_CODE = 100;

    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";
    private static String API_KEY = "";
//    private GooglePlacesAutocompleteAdapter mAdapter = null;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.searchCity:processPermissionsCheck();
                break;
        }
    }

    private void processPermissionsCheck() {
        if (ContextCompat.checkSelfPermission(mView.getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mView.getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSION_REQUEST_CODE);
        } else {
            invokePlaceSelector();
        }
    }

    @Override
    public void setView(PlaceSelectionContract.PlaceSelectionView view) {
        mView = (Fragment) view;
        API_KEY = ((Fragment) view).getString(R.string.google_maps_key);
//        mAdapter = new GooglePlacesAutocompleteAdapter(mView.getContext(), R.layout.place_list_item);
    }

//    public GooglePlacesAutocompleteAdapter getAdapter(){
//        return mAdapter;
//    }

    @Override
    public void invokePlaceSelector() {
        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(mView.getActivity());
            mView.startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }

    @Override
    public void handleactivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(mView.getActivity(), data);
                Log.i(TAG, "Place: " + place.getName());
                Intent intent = new Intent(mView.getContext() , AreaDetailsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("placeID", place.getId());
                mView.getContext().startActivity(intent);
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(mView.getActivity(), data);
                // TODO: Handle the error.
                Log.i(TAG, status.getStatusMessage());

            } else if (resultCode == Activity.RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }


//    class GooglePlacesAutocompleteAdapter extends ArrayAdapter implements Filterable {
//        private ArrayList resultList;
//
//        public GooglePlacesAutocompleteAdapter(Context context, int textViewResourceId) {
//            super(context, textViewResourceId);
//        }
//
//        @Override
//        public int getCount() {
//            return resultList.size();
//        }
//
//        @Override
//        public String getItem(int index) {
//            return (String) resultList.get(index);
//        }
//
//        @Override
//        public Filter getFilter() {
//            Filter filter = new Filter() {
//                @Override
//                protected FilterResults performFiltering(CharSequence constraint) {
//                    FilterResults filterResults = new Filter.FilterResults();
//                    if (constraint != null) {
//                        // Retrieve the autocomplete results.
//                        resultList = autocomplete(constraint.toString());
//
//                        // Assign the data to the FilterResults
//                        filterResults.values = resultList;
//                        filterResults.count = resultList.size();
//                    }
//                    return filterResults;
//                }
//
//                @Override
//                protected void publishResults(CharSequence constraint, FilterResults results) {
//                    if (results != null && results.count > 0) {
//                        notifyDataSetChanged();
//                    } else {
//                        notifyDataSetInvalidated();
//                    }
//                }
//            };
//            return filter;
//        }
//    }


//    public ArrayList autocomplete(String input) {
//        ArrayList resultList = null;
//
//        HttpURLConnection conn = null;
//        StringBuilder jsonResults = new StringBuilder();
//        try {
//            StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
//            sb.append("?key=" + API_KEY);
////            sb.append("&components=country:gr");
//            sb.append("&input=" + URLEncoder.encode(input, "utf8"));
//
//            URL url = new URL(sb.toString());
//            conn = (HttpURLConnection) url.openConnection();
//            InputStreamReader in = new InputStreamReader(conn.getInputStream());
//
//            // Load the results into a StringBuilder
//            int read;
//            char[] buff = new char[1024];
//            while ((read = in.read(buff)) != -1) {
//                jsonResults.append(buff, 0, read);
//            }
//        } catch (MalformedURLException e) {
//            Log.e(TAG, "Error processing Places API URL", e);
//            return resultList;
//        } catch (IOException e) {
//            Log.e(TAG, "Error connecting to Places API", e);
//            return resultList;
//        } finally {
//            if (conn != null) {
//                conn.disconnect();
//            }
//        }
//
//        try {
//            // Create a JSON object hierarchy from the results
//            JSONObject jsonObj = new JSONObject(jsonResults.toString());
//            JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");
//
//            // Extract the Place descriptions from the results
//            resultList = new ArrayList(predsJsonArray.length());
//            for (int i = 0; i < predsJsonArray.length(); i++) {
//                System.out.println(predsJsonArray.getJSONObject(i).getString("description"));
//                System.out.println("============================================================");
//                resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
//            }
//        } catch (JSONException e) {
//            Log.e(TAG, "Cannot process JSON results", e);
//        }
//
//        return resultList;
//    }
}
