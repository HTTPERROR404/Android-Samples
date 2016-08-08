package com.neji.weatherpoc.cityselection;

import com.neji.weatherpoc.BaseView;
import com.neji.weatherpoc.R;
import com.neji.weatherpoc.StatsListActivity;
import com.neji.weatherpoc.adapter.MyAdapter;
import com.neji.weatherpoc.data.model.City;
import com.neji.weatherpoc.utils.MyLocationListener;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.security.spec.EncodedKeySpec;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CitySelectionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CitySelectionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CitySelectionFragment extends Fragment implements BaseView, View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final long LOCATION_REFRESH_TIME = 1;
    private static final long LOCATION_REFRESH_DISTANCE = 10;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private CitySelectionPresenter mPresenter = null;
    EditText etCityInput = null;
    MyAdapter mAdapter = null;
    RecyclerView lv;
    ArrayList<City> dataArray = null;
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1; // 1 minute


    public CitySelectionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WeatherStatisticsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CitySelectionFragment newInstance(String param1, String param2) {
        CitySelectionFragment fragment = new CitySelectionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        mPresenter = new CitySelectionPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        SimpleBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_place_auto_complete, container, false);
        View view = binding.getRoot();
        View v = inflater.inflate(R.layout.fragment_place_auto_complete, container, false);

        lv = (RecyclerView) v.findViewById(R.id.listView);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        lv.setLayoutManager(mLinearLayoutManager);
        dataArray = new ArrayList<>();
        mAdapter = new MyAdapter(dataArray);
        lv.setAdapter(mAdapter);

        Button btnAddCity = (Button) v.findViewById(R.id.btnAddCity);
        btnAddCity.setOnClickListener(this);
        Button btnLoadForeCast = (Button) v.findViewById(R.id.btnShowWeather);
        btnLoadForeCast.setOnClickListener(this);
        etCityInput = (EditText) v.findViewById(R.id.etCityInput);
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }/* else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void loadForecast() {
        StatsListActivity frg = new StatsListActivity();
        frg.setCities(dataArray);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frgMain, frg).addToBackStack(null).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddCity:
                if (etCityInput.getText().length() > 0) {
                    mPresenter.performBtnAddCityClick(etCityInput.getText().toString());
                    dataArray.add(new City(etCityInput.getText().toString().trim()));
                }
                etCityInput.setText("");
                break;
            case R.id.btnShowWeather:
                loadForecast();
                break;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 0: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public void loadMyLocationWeather() {

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion <= android.os.Build.VERSION_CODES.LOLLIPOP){
            setLocationListener();
            return;
        }
        if (ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this.getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                showAlertDialog();
            } else {
              requestPermission();
            }

            return;
        }

    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(this.getActivity(),
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                0);
    }

    private void showAlertDialog(){
        new AlertDialog.Builder(this.getActivity())
                .setTitle("Permission disabled")
                .setMessage("This functionality wont work without Location permission")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        requestPermission();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    private void setLocationListener() {
        LocationManager mLocationManager = (LocationManager) this.getActivity().getSystemService(Context.LOCATION_SERVICE);
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME,
                LOCATION_REFRESH_DISTANCE, mPresenter);
    }

}
