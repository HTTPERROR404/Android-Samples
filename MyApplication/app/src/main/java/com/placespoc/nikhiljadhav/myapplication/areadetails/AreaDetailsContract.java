package com.placespoc.nikhiljadhav.myapplication.areadetails;

import android.content.Intent;
import android.view.View;

/**
 * Created by nikhil.jadhav on 4/8/16.
 */
public interface AreaDetailsContract {

    interface AreaDetailsView {

    }

    interface AreaDetailsPresentor extends View.OnClickListener{

        void setView(AreaDetailsView view, String id);

        void handleactivityResult(int requestCode, int resultCode, Intent data);

    }
}
