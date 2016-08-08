package com.placespoc.nikhiljadhav.myapplication.placeselection;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

/**
 * Created by nikhil.jadhav on 4/8/16.
 */
public interface PlaceSelectionContract {

    interface PlaceSelectionView{

    }

    interface PlaceSelectionPresentor extends View.OnClickListener, AdapterView.OnItemClickListener{

        void setView(PlaceSelectionView view);

        void invokePlaceSelector();

        void handleactivityResult(int requestCode, int resultCode, Intent data);

    }
}
