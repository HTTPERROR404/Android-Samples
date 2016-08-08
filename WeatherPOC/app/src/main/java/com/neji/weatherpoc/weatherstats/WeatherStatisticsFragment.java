package com.neji.weatherpoc.weatherstats;

import com.neji.weatherpoc.BaseView;
import com.neji.weatherpoc.R;
import com.neji.weatherpoc.data.model.List;
import com.neji.weatherpoc.data.model.WeatherStatsModel;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WeatherStatisticsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WeatherStatisticsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeatherStatisticsFragment extends Fragment implements WeatherStatisticsContract.View {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    WeatherStatsAdapter mAdapter;
    WeatherStatsModel model = null;
    RecyclerView mRecyclerView = null;
    private String mCity = "";

    private OnFragmentInteractionListener mListener;
    private WeatherStatisticsPresenter mPresenter = null;

    public WeatherStatisticsFragment() {
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
    public static WeatherStatisticsFragment newInstance(String param1, String param2) {
        WeatherStatisticsFragment fragment = new WeatherStatisticsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public void setCity(String city){
        mCity = city;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        mPresenter = new WeatherStatisticsPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.weather_stats_fragment, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.rlMain);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        if(mCity.trim().length()!=0) {
            mPresenter.loadJson(mCity, this);
        }else{
            mPresenter.loadJson(mCity, this);
        }

        TextView tvTitle = (TextView) v.findViewById(R.id.tvTitle);
        tvTitle.setText(mCity);

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

    @Override
    public void setProgressIndicator(boolean active) {

    }

    @Override
    public void showStatistics(int numberOfIncompleteTasks, int numberOfCompletedTasks) {

    }

    @Override
    public void showLoadingStatisticsError() {

    }

    @Override
    public boolean isActive() {
        return false;
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
    public void loadAdapter(WeatherStatsModel model){
        this.model = model;
        mAdapter = new WeatherStatsAdapter((ArrayList<List>) model.getList(), getContext());
        mRecyclerView.setAdapter(mAdapter);
    }

}
