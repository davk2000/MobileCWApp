package com.example.dav.mobilecwapp;



import android.Manifest;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.app.DialogFragment;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class mcMapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
 // This class provides the main map containing marker locations for Glasgow, Edinburgh & Inverness
 // and is also responsible for changing the map view
    RadioButton Normal, Hybrid, Satellite, Terrain; // variables used to change map view



    public void playSound(){ // plays audio file for a button click on the submit and access entries buttons
        MediaPlayer mp = MediaPlayer.create(this, R.raw.button_click);
        mp.start();
    }

    public void playSound2(){ // plays audio file for a button click in the action bar
        MediaPlayer mp = MediaPlayer.create(this, R.raw.toolbar_click);
        mp.start();
    }

    FragmentManager fmAboutDialogue; // Lab 3 Dialogue fragment
    List<mcMapData> mapDataLst;
    private Marker[] mapDataMarkerList = new Marker[5];
    private GoogleMap map; //Google Map variable
    private float markerColours[] = {210.0f, 120.0f, 300.0f, 330.0f, 270.0f};
    private static final LatLng latlangEKCentre = new LatLng(56.5831, -
            4.0374); //The Latitude and Longitude for Scotland
    SupportMapFragment mapFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mc_map_view); // app main UI screen

        fmAboutDialogue = this.getFragmentManager(); // about dialog variable
        mapDataLst = new ArrayList<mcMapData>();
        mcMapDataDBMgr mapDB = new mcMapDataDBMgr(this, "locationDatabase.s3db", null,
                1);
        try {
            mapDB.dbCreate();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mapDataLst = mapDB.allMapData();
        SetUpMap(); // method called in to start google maps

        Normal = (RadioButton)findViewById(R.id.rdoNormal); //change map view variables instantiated
        Hybrid = (RadioButton)findViewById(R.id.rdoHybrid); //change map view variables instantiated
        Satellite = (RadioButton)findViewById(R.id.rdoSatellite); //change map view variables instantiated
        Terrain = (RadioButton)findViewById(R.id.rdoTerrain); //change map view variables instantiated
        Normal.setOnClickListener(myOptionOnClickListener); //invokes event
        Hybrid.setOnClickListener(myOptionOnClickListener); //invokes event
        Satellite.setOnClickListener(myOptionOnClickListener); //invokes event
        Terrain.setOnClickListener(myOptionOnClickListener); //invokes event


    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        if (map != null) {

            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlangEKCentre,
                    7)); //Sets the default position to Scotland
            map.getUiSettings().setCompassEnabled(true); //Turn on the Compass

            map.getUiSettings().setMyLocationButtonEnabled(true); //Turn on the Location Buttons Functionality

            map.getUiSettings().setRotateGesturesEnabled(true);
            AddMarkers();
        }
    }
    public void SetUpMap() {
        MapFragment mapFragment = (MapFragment)
                getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this); //Create the map and apply to the variable
    }

    public void AddMarkers() {
        MarkerOptions marker;
        mcMapData mapData;
        String mrkTitle;
        String mrkText;
 // For all the marker options in dbList list
        for (int i = 0; i < mapDataLst.size(); i++) {
            mapData = mapDataLst.get(i);
            mrkTitle = mapData.getlocation_Name() + " " + mapData.getpostcode_District();

            marker = SetMarker(mrkTitle, new LatLng(mapData.getLatitude(),
                    mapData.getLongitude()), markerColours[i], true);
            mapDataMarkerList[i] = map.addMarker(marker); //creates a maker and adds it to the venue markers list
        }
    }
    public MarkerOptions SetMarker(String title, LatLng position, float markerColour, boolean centreAnchor) {
        float anchorX; //Create anchorX
        float anchorY; //Create anchorY
 /* On the condition the anchor is to be centred */
        if (centreAnchor) {
            anchorX = 0.5f; //Centre X
            anchorY = 0.5f; //Centre Y
        } else {
            anchorX = 0.5f; //Centre X
            anchorY = 1f; //Bottom Y
        }
 // creates marker options from the input variables
        MarkerOptions marker = new
                MarkerOptions().title(title).icon(BitmapDescriptorFactory.defaultMarker(markerColour)).anchor(anchorX, anchorY).position(position);
        return marker; //Return marker
    }
    // Menu

    RadioButton.OnClickListener myOptionOnClickListener = new RadioButton.OnClickListener() {



        public void onClick(View v) {
           // This method is called when event takes after selecting a radiobutton which changes the map view depending on 4 options
            if(Normal.isChecked())
            {

                map.setMapType(com.google.android.gms.maps.GoogleMap.MAP_TYPE_NORMAL);

            }else if(Hybrid.isChecked())

            {

                map.setMapType(com.google.android.gms.maps.GoogleMap.MAP_TYPE_HYBRID);

            }else if(Satellite.isChecked())

            {

                map.setMapType(com.google.android.gms.maps.GoogleMap.MAP_TYPE_SATELLITE);

            }else if(Terrain.isChecked())

            {
                map.setMapType(com.google.android.gms.maps.GoogleMap.MAP_TYPE_TERRAIN);
            }

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
        //return true;
        //}
        switch (item.getItemId()) {

            case R.id.action_settings:
                playSound2();

                SavedPreference();


                return true;

            case R.id.action_settings2:
                playSound2();

                Map();


                return true;

            case R.id.action_settings3:
                playSound2();

                WeatherForecast();


                return true;

            case R.id.action_settings4:
                playSound2();

                PieChart();


                return true;

            case R.id.action_settings5:
                playSound2();

                LocationDetails();


                return true;

            case R.id.action_settings6:
                playSound2();

                showExitDialog();


                return true;

            case R.id.about:
                // About Dialogue;
                playSound2();

                DialogFragment mcAboutDlg = new AboutDialogue();
                mcAboutDlg.show(fmAboutDialogue, "menu");
                return true;
            default:

                return super.onOptionsItemSelected(item);
        }


    }

    private void SavedPreference() {
        Intent i = new Intent(mcMapActivity.this, RssInput.class);
        startActivity(i); // called from the actionbar
        //shortcut to the saved preference page
    }

    private void Map() {
        Intent i = new Intent(mcMapActivity.this, mcMapActivity.class);
        startActivity(i); // called from the actionbar
        //shortcut to the map page
    }

    private void WeatherForecast() {
        Intent i = new Intent(mcMapActivity.this, RssInput.class);
        startActivity(i); // called from the actionbar
        //shortcut to the Weather forecast spinner input page
    }

    private void PieChart() {
        Intent i = new Intent(mcMapActivity.this, PiechartActivity.class);
        startActivity(i); // called from the actionbar
        //shortcut to the Piechart and accelerometer page
    }

    private void LocationDetails() {
        Intent i = new Intent(mcMapActivity.this, DatabaseActivity.class);
        startActivity(i); // called from the actionbar
        //shortcut to the database
    }




    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.e("GoogleMapActivity", "onMarkerClick");
        if (marker.equals(mapDataMarkerList[0])) {
            Intent i = new Intent(mcMapActivity.this, RSS.class);
            startActivity(i);
            return true;
        }

        return true;
    }

    private void showExitDialog() { // called from both the main menu and the action bar

        // Dialog box for exiting the application providing options yes or no options before closing application

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Closing Application"); // heading for the dialog box
        builder.setMessage("Are you sure you want to exit?"); // message that appear below the heading
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id)
            { // option 1
                Toast.makeText(getApplicationContext(), "You Pressed Yes", Toast.LENGTH_SHORT).show();
                mcMapActivity.this.finish(); // if option 1 is selected application closing
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id)
            { // option 2
                Toast.makeText(getApplicationContext(), "You Pressed No", Toast.LENGTH_SHORT).show();
                dialog.cancel(); // if option 2 is selected the main activity continues
            }
        });
        AlertDialog alert = builder.create();
        alert.show();


    }

}

