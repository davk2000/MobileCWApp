package com.example.dav.mobilecwapp;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import java.util.ArrayList;
import java.util.List;
import android.widget.AdapterView;
import android.view.View;
import android.widget.Toast;
import android.content.Intent;



//rss_input_page which utilises a spinner and a saved preference

public class RssInput extends AppCompatActivity {


    //class variables
    Spinner spinner;
    String[] Values = {"Glasgow","Edinburgh","Inverness"}; // array which contains valus for the spinner
    String SpinnerValue;
    Button submitButton;
    Button requests;
    Intent intent;
    private SharedPreferences prefs;
    private String prefName = "spinner_value";
    int id=0; // used as the default item selection for saved preference
    FragmentManager fmAboutDialogue;

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
        setContentView(R.layout.rss_input_page);

        fmAboutDialogue = this.getFragmentManager(); // about dialog variable

        spinner =(Spinner)findViewById(R.id.spinner);

        submitButton = (Button)findViewById(R.id.button10);

        requests = (Button)findViewById(R.id.button20); // button used for writetob intent

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(RssInput.this, android.R.layout.simple_list_item_1, Values); // required for onItemSelected in spinner

        spinner.setAdapter(adapter);

        prefs = getSharedPreferences(prefName, MODE_PRIVATE); // acquires item selected based on int value
        id=prefs.getInt("last_val",0);
        spinner.setSelection(id);



        spinner.setOnItemSelectedListener(new OnItemSelectedListener() { //Adds setOnItemSelectedListener method on spinner for both item selected in spinner and saved preference

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                SpinnerValue = (String)spinner.getSelectedItem(); //assigned to spinner to identify the item being selected
                prefs = getSharedPreferences(prefName, MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit(); //saves the value in the spinner and spinner keeps item as the selected item within the list

                editor.putInt("last_val", position);


                editor.commit(); //saves the values and displays message to confirm this
                Toast.makeText(getApplicationContext(), "Your preference has been saved", Toast.LENGTH_SHORT).show();

                Toast.makeText(getBaseContext(), spinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });



        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) { // handles event depending spinner option selected
                // TODO Auto-generated method stub
                playSound();




                switch(SpinnerValue){

                    case "Glasgow": // Accesses rss xml link for Glasgow
                        intent = new Intent(RssInput.this, RSS.class);
                        startActivity(intent);
                        break;

                    case "Edinburgh": // Accesses rss xml link for Edinburgh
                        intent = new Intent(RssInput.this, RSS2.class);
                        startActivity(intent);
                        break;

                    case "Inverness": // Accesses rss xml link for Inverness
                        intent = new Intent(RssInput.this, RSS3.class);
                        startActivity(intent);
                        break;


                }
            }
        });

        requests.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) { // intent used to take user to writetodb page
                // TODO Auto-generated method stub
                playSound();


                        intent = new Intent(RssInput.this, Writetodb.class);
                        startActivity(intent);
            }
        });

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

        switch (item.getItemId()) {

            case R.id.action_settings:

                SavedPreference();
                playSound();


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
        Intent i = new Intent(RssInput.this, RssInput.class);
        startActivity(i); // called from the actionbar
        //shortcut to the saved preference page
    }

    private void Map() {
        Intent i = new Intent(RssInput.this, mcMapActivity.class);
        startActivity(i); // called from the actionbar
        //shortcut to the map page
    }

    private void WeatherForecast() {
        Intent i = new Intent(RssInput.this, RssInput.class);
        startActivity(i); // called from the actionbar
        //shortcut to the Weather forecast spinner input page
    }

    private void PieChart() {
        Intent i = new Intent(RssInput.this, PiechartActivity.class);
        startActivity(i); // called from the actionbar
        //shortcut to the Piechart and accelerometer page
    }

    private void LocationDetails() {
        Intent i = new Intent(RssInput.this, DatabaseActivity.class);
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
                RssInput.this.finish(); // if option 1 is selected application closing
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