package com.example.dav.mobilecwapp;

/**
 * Created by Davoud on 05/12/2016.
 */

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
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

public class WeatherDetail2 extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
//this class is used when user selects a listview item within RSS Feed for Edinburgh

    FragmentManager fmAboutDialogue; // Variable used for about dialog box
    List<mcMapData> mapDataLst;
    private Marker[] mapDataMarkerList = new Marker[3];
    private GoogleMap mapLocations; //Google Map variable
    private float markerColours[] = {210.0f, 120.0f, 300.0f};
    private static final LatLng latlangEKCentre = new LatLng(55.9520, -
            3.1964); //The Latitude and Longitude for Edinburgh

    ;

    SupportMapFragment mapFragment;

    public void playSound(){ // plays audio file for a button click on the submit and access entries buttons
        MediaPlayer mp = MediaPlayer.create(this, R.raw.button_click);
        mp.start();
    }

    public void playSound2(){ // plays audio file for a button click in the action bar
        MediaPlayer mp = MediaPlayer.create(this, R.raw.toolbar_click);
        mp.start();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rss_detail);

        Bundle content = this.getIntent().getExtras(); //declares variable content which is then instantiated to display description tag


        String desc = content.getString("description"); //locates description text based on labelled tag for description in the getView method


        TextView tvdesc = (TextView) findViewById(R.id.tvdesc); // textview assigned to the description tag which displays relevant data


        tvdesc.setText(desc); //sets description to whatever has been parsed


        fmAboutDialogue = this.getFragmentManager(); // called as a paramter for the about dialog box
        mapDataLst = new ArrayList<mcMapData>();
        mcMapDataDBMgr mapDB = new mcMapDataDBMgr(this, "locationDatabase.s3db", null,
                1);
        try {
            mapDB.dbCreate();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mapDataLst = mapDB.allMapData();
        SetUpMap();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapLocations = googleMap;
        if (mapLocations != null) {

            mapLocations.moveCamera(CameraUpdateFactory.newLatLngZoom(latlangEKCentre,
                    11)); //Set the default position to EK
            mapLocations.getUiSettings().setCompassEnabled(true); //Turn on the Compass

            mapLocations.getUiSettings().setMyLocationButtonEnabled(true); //Turn on the Location Buttons Functionality

            mapLocations.getUiSettings().setRotateGesturesEnabled(true);
            AddMarkers();
        }
    }
    public void SetUpMap() {
        MapFragment mapFragment = (MapFragment)
                getFragmentManager().findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this); //Create the map and apply to the variable

    }

    public void AddMarkers() {
        MarkerOptions marker;
        mcMapData mapData;
        String mrkTitle;
        String mrkText;
 // For all the marker options in DatabaseList list
        for (int i = 0; i < mapDataLst.size(); i++) {
            mapData = mapDataLst.get(i);
            mrkTitle = mapData.getlocation_Name() + " " + mapData.getpostcode_District();

            marker = SetMarker(mrkTitle, new LatLng(mapData.getLatitude(),
                    mapData.getLongitude()), markerColours[i], true);
            mapDataMarkerList[i] = mapLocations.addMarker(marker); //creates a maker and adds it to the markers list
        }
    }
    public MarkerOptions SetMarker(String title, LatLng position, float markerColour, boolean centreAnchor) {
        float anchorX; //Create anchorX
        float anchorY; //Create anchorY
 // On the condition the anchor is to be centred
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

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu); // provides access to items which reference the action bar buttons
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.



        switch (item.getItemId()) { // action bar calls for each button in the settings

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
        Intent i = new Intent(WeatherDetail2.this, RssInput.class);
        startActivity(i); // called from the actionbar
        //shortcut to the saved preference page
    }

    private void Map() {
        Intent i = new Intent(WeatherDetail2.this, mcMapActivity.class);
        startActivity(i); // called from the actionbar
        //shortcut to the map page
    }

    private void WeatherForecast() {
        Intent i = new Intent(WeatherDetail2.this, RssInput.class);
        startActivity(i); // called from the actionbar
        //shortcut to the Weather forecast spinner input page
    }

    private void PieChart() {
        Intent i = new Intent(WeatherDetail2.this, PiechartActivity.class);
        startActivity(i); // called from the actionbar
        //shortcut to the Piechart and accelerometer page
    }

    private void LocationDetails() {
        Intent i = new Intent(WeatherDetail2.this, DatabaseActivity.class);
        startActivity(i); // called from the actionbar
        //shortcut to the database
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
                WeatherDetail2.this.finish(); // if option 1 is selected application closing
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
