package com.example.dav.mobilecwapp;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.content.Context;
import android.widget.Button;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.DialogInterface;
import android.widget.Toast;
import android.app.FragmentManager;
import android.app.DialogFragment;

public class MainActivity extends AppCompatActivity implements OnClickListener {
// main menu screen

    private Button button2; // Map Button
    private Button button3; // Location Details button (pre-populated database page)
    private Button exitButton; // Exit button on main menu
    private Button button5; // Weather Forecast button
    private Button button19; // Pie chart button
    FragmentManager fmAboutDialogue; // about dialog variable

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
        setContentView(R.layout.activity_main);

        addListenerOnButton();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        fmAboutDialogue = this.getFragmentManager(); // called as a paramter for the about dialog box


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
        Intent i = new Intent(MainActivity.this, RssInput.class);
        startActivity(i); // called from the actionbar
        //shortcut to the saved preference page
    }

    private void Map() {
        Intent i = new Intent(MainActivity.this, mcMapActivity.class);
        startActivity(i); // called from the actionbar
        //shortcut to the map page
    }

    private void WeatherForecast() {
        Intent i = new Intent(MainActivity.this, RssInput.class);
        startActivity(i); // called from the actionbar
        //shortcut to the Weather forecast spinner input page
    }

    private void PieChart() {
        Intent i = new Intent(MainActivity.this, PiechartActivity.class);
        startActivity(i); // called from the actionbar
        //shortcut to the Piechart and accelerometer page
    }

    private void LocationDetails() {
        Intent i = new Intent(MainActivity.this, DatabaseActivity.class);
        startActivity(i); // called from the actionbar
        //shortcut to the database
    }

    public void addListenerOnButton() {

        // handles all button listeners for the main menu excluding action bar
        final Context context = this;


        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button5 = (Button) findViewById(R.id.button5);
        button19 = (Button) findViewById(R.id.button19);
        exitButton = (Button)findViewById(R.id.button4);
        exitButton.setOnClickListener(this);



        button2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) { // handles intent for navigating to map page

                playSound();


                Intent intent = new Intent(context, mcMapActivity.class);
                startActivity(intent);



            }

        });

        button3.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) { // handles intent for navigating to pre-populated database page

                playSound();
                Intent intent = new Intent(context, DatabaseActivity.class);
                startActivity(intent);

            }

        });

        button5.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) { // handles intent for navigating to page with spinner options

                playSound();


                Intent intent = new Intent(context, RssInput.class);
                startActivity(intent);

            }

        });

        button19.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) { // handles intent for navigating to pie chart page

                playSound();

                Intent intent = new Intent(context, PiechartActivity.class);
                startActivity(intent);

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
                MainActivity.this.finish(); // if option 1 is selected application closing
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



    // handles exit button event
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.button4:
                playSound();
                showExitDialog();
                break;




        }
    }


}
