package com.immad.sabahat.buzzmovetest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import beans.Place;

public class MapsActivity extends AppCompatActivity implements GoogleMap.OnMarkerClickListener, OnMapReadyCallback {

    private GoogleMap mMap;
    String myLoc;
    public ArrayList<Place> placeArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        myLoc = getIntent().getStringExtra("myLoc");
        placeArr = (ArrayList<Place>) getIntent().getSerializableExtra("placeArray");
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //show pointer at your location
        String[] latlng = myLoc.split(",");
        LatLng here = new LatLng(Double.parseDouble(latlng[0]), Double.parseDouble(latlng[1]));
        mMap.addMarker(new MarkerOptions().position(here).title("You are here.").
                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)))
        .setTag("me");
        //Build camera position
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(here)
                .zoom(13).build();
        //Zoom in and animate the camera.
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        //show all markers in search
        for(int i = 0; i<placeArr.size();i++) {

                Place place = placeArr.get(i);
                LatLng results = new LatLng(Double.parseDouble(place.getLat()), Double.parseDouble(place.getLng()));
                mMap.addMarker(new MarkerOptions().position(results).title(place.getName())).setTag(i);

        }

        // Set a listener for marker click.
        mMap.setOnMarkerClickListener(this);
    }

    /** Called when the user clicks a marker. */
    @Override
    public boolean onMarkerClick(final Marker marker) {

        if(!marker.getTag().toString().equals("me")) {
            Intent intent = new Intent(MapsActivity.this, DescriptionActivity.class);
            intent.putExtra("place", placeArr.get(Integer.parseInt(marker.getTag().toString())));
            startActivity(intent);
        }


        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }
}
