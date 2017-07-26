package project.harsh.com.yoblunttask;

import android.Manifest;
import android.app.Activity;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {


    public List<CardData> cardDataList;
    CardScrollerFragment cardScrollerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        cardScrollerFragment = new CardScrollerFragment();
        checkLocationPermission();
        String string = loadJSONFromAsset();

        try {
            JSONArray jsonArray = new JSONArray(string);
            cardDataList = jsonToCardDataList(jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        /*-----ADDING Fragment--------------*/

        FragmentTransaction ft = getFragmentManager().beginTransaction();

        ft.replace(R.id.fragment_container, cardScrollerFragment);
        // ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }
/*----------------Load JSON from Assets folder-------------------*/
    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getResources().openRawResource(R.raw.data);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    /*-----------------Initialize Card DATA ----------------------------*/

    public List<CardData> jsonToCardDataList(JSONArray jsonArray) {
        List<CardData> cardDataList = new ArrayList<>();

        LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        boolean network_enabled = locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        Location location=null;

        if (network_enabled) {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            }
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                location = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            }

        }






        for(int i=0;i<jsonArray.length();i++){

            try {
                JSONObject object= jsonArray.getJSONObject(i);
                String imgUrl = object.getString("thumbnail");
                String title= object.getString("title");
                String tag= object.getString("tag");
                JSONObject coordinateObject= object.getJSONObject("location");
                String lan= coordinateObject.getString("lan");
                String lng= coordinateObject.getString("lng");
                LatLng latLng= new LatLng(Double.parseDouble(lan),Double.parseDouble(lng));
                Location temp = new Location(LocationManager.GPS_PROVIDER);
                temp.setLatitude(latLng.latitude);
                temp.setLongitude(latLng.longitude);
                float distance;
                if(location!=null)
                distance = location.distanceTo(temp);
                else
                    distance=0;

                CardData cardData= new CardData(title,tag,imgUrl,latLng,distance);

                cardDataList.add(cardData);

            }
            catch (JSONException e){
                Log.e("Exception",e.getMessage().toString());
            }
        }
        Collections.sort(cardDataList);
        return  cardDataList;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
/*-----------------Setting focused listener----------------------*/
        cardScrollerFragment.setFocusListener(googleMap);
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;





    /*--------------------Allow location [ermission code----------------*/

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission. ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission. ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("LOcation Permission")
                        .setMessage("Device Needs to acess your location. Do you want to allow?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission. ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission. ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {



                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }






}
