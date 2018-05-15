package com.immad.sabahat.buzzmovetest;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import beans.Place;
import handlerclasses.AlertDialogHandler;
import handlerclasses.InternetConnectionDetector;
import handlerclasses.SessionManagement;
import handlerclasses.WebserviceHandler;

public class MainActivity extends AppCompatActivity{
    static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    private AlertDialogHandler alert;
    private Button mapBtn;
    private EditText searchEt;
    private Button searchBtn;
    private WebserviceHandler webserviceHandler;
    ProgressBar progressBar;
    private ListView listView;
    public ArrayList<Place> placeArr;
    public String currentLoc = "";
    SessionManagement sessionManager;
    private InternetConnectionDetector internet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapBtn = findViewById(R.id.map_btn);
        searchEt = findViewById(R.id.place_et);
        searchBtn = findViewById(R.id.search_btn);
        progressBar = findViewById(R.id.progress);
        listView = findViewById(R.id.listview);
        alert = new AlertDialogHandler();

        webserviceHandler = new WebserviceHandler(this);
        internet = new InternetConnectionDetector(this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        sessionManager = new SessionManagement(this);

        //for offline work
        if(!internet.isConnectingToInternet()){
            if(sessionManager.isOfflineData()){
                searchEt.setText(sessionManager.getOfflineName());
                getLocation();
            }
            else{
                Toast.makeText(this, "no offline record present, please connect internet" +
                        "and try again", Toast.LENGTH_SHORT).show();
            }
        }

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(internet.isConnectingToInternet()) {
                    showProgressBar(true);
                    getLocation();
                }
                else {
                    Toast.makeText(MainActivity.this, "no offline record present, please connect internet" +
                            " and try again", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, MapsActivity.class);
                i.putExtra("placeArray", placeArr);
                if(!currentLoc.equals("")) {
                    i.putExtra("myLoc", currentLoc);
                    startActivity(i);
                }
                else if (sessionManager.isOfflineData()) {
                    i.putExtra("myLoc", sessionManager.getOfflineLocation());
                    startActivity(i);
                }
                else{
                    Toast.makeText(MainActivity.this, "no offline record present, please connect internet" +
                            "and try again", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }

    /**
     * Gets user's current location with GPS and if its enabled then search the keyword using Google
     * nearby place API
     */
    public void getLocation(){


        if(internet.isConnectingToInternet()) {
            //for offline work
            sessionManager.saveName(searchEt.getText().toString());
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                    (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

            } else {
                Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                if (location != null) {
                    double latti = location.getLatitude();
                    double longi = location.getLongitude();


                    Log.d("Gps Location", "Current Location is :" + latti + "," + longi);
                    currentLoc = latti + "," + longi;
                    sessionManager.saveCurrLoc(currentLoc);
                    webserviceHandler.getStringRequest(webserviceHandler.urlParams(currentLoc,
                            "", searchEt.getText().toString()));


                } else {

                    alert.showAlertDialog(this, "oops", "We are unable to " +
                            "get your current location this time. Please try again later");
                }

            }
        }
        else{
            progressBar.setVisibility(View.GONE);
            webserviceHandler.parseJsonResponse(sessionManager.getOfflineResult());
        }
    }

    /**
     * Handles the visibility of progress bar
     * @param isShow set true to show and false to hide
     */
    public void showProgressBar(Boolean isShow){

        if(isShow)
            progressBar.setVisibility(View.VISIBLE);
        else
            progressBar.setVisibility(View.GONE);

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_LOCATION:
                getLocation();
                break;
        }
    }

    /**
     * Sets the listview items when the places api data is fetched
     *
     * @param placesArray
     */
    public void setListViewAdapter(ArrayList<Place> placesArray){

        // Check if no view has focus, then hide keyboard
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        mapBtn.setVisibility(View.VISIBLE);
        CustomAdapter adapter = new CustomAdapter(placesArray,this);
        listView.setAdapter(adapter);

    }
    }


