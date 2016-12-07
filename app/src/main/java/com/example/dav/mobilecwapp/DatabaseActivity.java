package com.example.dav.mobilecwapp;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dav.mobilecwapp.R;


public class DatabaseActivity extends AppCompatActivity {
    // used for database called databaseinfo.s3db
    // This class provides 3 rows of data within database and displays location, coordinates and postcodes
    SqLiteDbHelper dbHelper;
    DatabaseDetails bbcWeather ;// variable for textview row 1 of the database
    DatabaseDetails bbcWeather2 ; //variable for textview row 2 of the database
    DatabaseDetails bbcWeather3 ; //variable for textview row 3 of the database
    FragmentManager fmAboutDialogue; // variable used for the about dialog box
    Button databaseButton; // button used to start mcMapActivity

    public void playSound(){ // plays audio file for a button click on the submit and access entries buttons
        MediaPlayer mp = MediaPlayer.create(this, R.raw.button_click);
        mp.start();
    }

    public void playSound2(){ // plays audio file for a button click in the action bar
        MediaPlayer mp = MediaPlayer.create(this, R.raw.toolbar_click);
        mp.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.database_info);

        fmAboutDialogue = this.getFragmentManager();

        addListenerOnButton();

        dbHelper = new SqLiteDbHelper(this);
        dbHelper.openDataBase();
        bbcWeather = new DatabaseDetails();
        bbcWeather = dbHelper.Get_WeatherDetails();// variables instantiated in order to present data for 3 textviews within database_info.xml
        bbcWeather2 = dbHelper.Get_WeatherDetails2();
        bbcWeather3 = dbHelper.Get_WeatherDetails3();
        TextView tv1 = (TextView)findViewById(R.id.textView1);
        TextView tv2 = (TextView)findViewById(R.id.textView);
        TextView tv3 = (TextView)findViewById(R.id.textView3);

        //This is reference to the 3 methods within SqLiteDbHelper class for capturing data of all 3 rows within databaseinfo.s3db
        tv1.setText("Location Name: "+bbcWeather.getLocation_name()+"\nPostcode District: "+bbcWeather.getPostcode_District()+"\nCoordinates: "+bbcWeather.getCoordinates());
        tv2.setText("Location Name: "+bbcWeather2.getLocation_name()+"\nPostcode District: "+bbcWeather2.getPostcode_District()+"\nCoordinates: "+bbcWeather2.getCoordinates());
        tv3.setText("Location Name: "+bbcWeather3.getLocation_name()+"\nPostcode District: "+bbcWeather3.getPostcode_District()+"\nCoordinates: "+bbcWeather3.getCoordinates());

    }

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
                playSound2();
                // About Dialogue;
                DialogFragment mcAboutDlg = new AboutDialogue();
                mcAboutDlg.show(fmAboutDialogue, "menu");
                return true;
            default:

                return super.onOptionsItemSelected(item);
        }


    }

    private void SavedPreference() {
        Intent i = new Intent(DatabaseActivity.this, RssInput.class);
        startActivity(i); // called from the actionbar
        //shortcut to the saved preference page
    }

    private void Map() {
        Intent i = new Intent(DatabaseActivity.this, mcMapActivity.class);
        startActivity(i); // called from the actionbar
        //shortcut to the map page
    }

    private void WeatherForecast() {
        Intent i = new Intent(DatabaseActivity.this, RssInput.class);
        startActivity(i); // called from the actionbar
        //shortcut to the Weather forecast spinner input page
    }

    private void PieChart() {
        Intent i = new Intent(DatabaseActivity.this, PiechartActivity.class);
        startActivity(i); // called from the actionbar
        //shortcut to the Piechart and accelerometer page
    }

    private void LocationDetails() {
        Intent i = new Intent(DatabaseActivity.this, DatabaseActivity.class);
        startActivity(i); // called from the actionbar
        //shortcut to the database
    }

    public void addListenerOnButton() {

        final Context context = this;


        databaseButton = (Button) findViewById(R.id.button27);

        databaseButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {


                Intent intent = new Intent(context, mcMapActivity.class);
                startActivity(intent);
                playSound();



            }

        });

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
                DatabaseActivity.this.finish(); // if option 1 is selected application closing
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
