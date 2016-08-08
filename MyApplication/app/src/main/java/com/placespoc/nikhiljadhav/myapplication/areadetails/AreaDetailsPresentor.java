package com.placespoc.nikhiljadhav.myapplication.areadetails;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResult;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by nikhil.jadhav on 4/8/16.
 */
public class AreaDetailsPresentor implements AreaDetailsContract.AreaDetailsPresentor {

    private static final String TAG = "AreaDetailsPresentor";
    private Fragment mView = null;
    private final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private GoogleApiClient mGoogleApiClient;
    private PlacePhotoMetadataBuffer photoMetadataBuffer;
    private String mPlaceID = "";


    private final ArrayList<AreaDetailsPresentor.PhotoTask.AttributedPhoto> dataset = new ArrayList<>();

    public PhotoAdaptor getAdaptor() {
        return mAdaptor;
    }

    private PhotoAdaptor mAdaptor;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
        }
    }

    @Override
    public void setView(AreaDetailsContract.AreaDetailsView view, String id) {
        mView = (Fragment) view;
        this.mPlaceID = id;
        mGoogleApiClient = new GoogleApiClient.Builder(mView.getContext())
                .enableAutoManage(mView.getActivity()/* FragmentActivity */,
                        (GoogleApiClient.OnConnectionFailedListener) mView/* OnConnectionFailedListener */)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();
        mAdaptor = new PhotoAdaptor((AreaDetailsFragment) mView, dataset, this);
        mPlaceID = id;
    }


    @Override
    public void handleactivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(mView.getActivity(), data);
                Log.i(TAG, "Place: " + place.getName());

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(mView.getActivity(), data);
                // TODO: Handle the error.
                Log.i(TAG, status.getStatusMessage());

            } else if (resultCode == Activity.RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }

    }

    public void handleItemClick(final int position){
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    saveFile(photoMetadataBuffer.get(position).getPhoto(mGoogleApiClient).await().getBitmap());
                }
            });
        Toast.makeText(mView.getContext(), "Picture will be downloaded in Pictures/saved_images folder", Toast.LENGTH_SHORT).show();
        thread.start();
    }

    private static void saveFile(Bitmap finalBitmap){

        String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        File myDir = new File(root + "/saved_images");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".jpg";
        File file = new File(myDir, fname);
        if (file.exists())
            file.delete();

        try {
            file.createNewFile();
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void placePhotosTask() {
        final String placeId = mPlaceID; // Australian Cruise Group

        // Create a new AsyncTask that displays the bitmap and attribution once loaded.
        new PhotoTask(40, 40) {
            @Override
            protected void onPreExecute() {
                // Display a temporary image to show while bitmap is loading.
//                imageView.setImageResource(R.drawable.progress_animation);
//                super.mPosition = position;
            }

            @Override
            protected void onPostExecute(AttributedPhoto attributedPhoto) {
                if (attributedPhoto != null) {
                    // Photo has been loaded, display it.
//                    imageView.setImageBitmap(attributedPhoto.bitmap);

                }
            }
        }.execute(placeId);
    }

    abstract class PhotoTask extends AsyncTask<String, Void, PhotoTask.AttributedPhoto> {

        private final int mHeight;

        private final int mWidth;

        private int mPosition;

        public PhotoTask(int width, int height) {
            mHeight = height;
            mWidth = width;
        }

        /**
         * Loads the first photo for a place id from the Geo Data API.
         * The place id must be the first (and only) parameter.
         */
        @Override
        protected AttributedPhoto doInBackground(String... params) {
            if (params.length != 1) {
                return null;
            }
            AttributedPhoto attributedPhoto = null;
            if(photoMetadataBuffer == null) {
                PlacePhotoMetadataResult result = Places.GeoDataApi
                        .getPlacePhotos(mGoogleApiClient, mPlaceID).await();
                photoMetadataBuffer = result.getPhotoMetadata();
//                notifyDataSetChanged();
            }
            if (photoMetadataBuffer.getCount() > 0 ) {
                // Get the first bitmap and its attributions.
                Iterator<PlacePhotoMetadata> iterator = photoMetadataBuffer.iterator();
                while(iterator.hasNext()) {
                    PlacePhotoMetadata photo = iterator.next();
                    CharSequence attribution = photo.getAttributions();
                    // Load a scaled bitmap for this photo.
                    Bitmap image = /*photo.getPhoto(mGoogleApiClient).await().getBitmap();*/ photo.getScaledPhoto(mGoogleApiClient, mWidth, mHeight).await()
                            .getBitmap();

                    attributedPhoto = new AttributedPhoto(attribution, image);
                    addItem(attributedPhoto);
                }
            }
            // Release the PlacePhotoMetadataBuffer.
//                photoMetadataBuffer.release();

            return attributedPhoto;
        }

        /**
         * Holder for an image and its attribution.
         */
        class AttributedPhoto {

            public final CharSequence attribution;

            public final Bitmap bitmap;

            public AttributedPhoto(CharSequence attribution, Bitmap bitmap) {
                this.attribution = attribution;
                this.bitmap = bitmap;
            }
        }
    }

    private void addItem(AreaDetailsPresentor.PhotoTask.AttributedPhoto item) {
        dataset.add(item);
        mView.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdaptor.notifyDataSetChanged();
            }
        });
    }

    public void onDestroy(){
        if (photoMetadataBuffer != null) {
            photoMetadataBuffer.release();
        }

    }

}
