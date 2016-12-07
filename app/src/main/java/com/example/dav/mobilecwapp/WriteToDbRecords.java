package com.example.dav.mobilecwapp;



import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
// writing to database - accessing entries

public class WriteToDbRecords extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextName; // displays value previously entered by user
    private EditText editTextId; // displays row id from database
    private Button buttonPrev; // previous entry button
    private Button buttonNext; // next entry button

    private static final String SELECT_SQL = "SELECT * FROM persons"; // accesses database with any data entered by user

    private SQLiteDatabase database; // required for SQL Queries to open database

    private Cursor cursor; // variable used to scan rows within database

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
        setContentView(R.layout.write_to_db_records);
        openDatabase();

        editTextId = (EditText) findViewById(R.id.editTextId);
        editTextName = (EditText) findViewById(R.id.editTextName);


        buttonPrev = (Button) findViewById(R.id.btnPrev);
        buttonNext = (Button) findViewById(R.id.btnNext);


        buttonNext.setOnClickListener(this);
        buttonPrev.setOnClickListener(this);


        cursor = database.rawQuery(SELECT_SQL, null); // used to show the first value which was entered by the user
        cursor.moveToFirst();
        showRecords();
        fmAboutDialogue = this.getFragmentManager(); // about dialog variable
    }

    protected void openDatabase() { // opens the previously created database within the Writetodb class
        database = openOrCreateDatabase("PersonDB", Context.MODE_PRIVATE, null);
    }

    protected void showRecords() { // method called when button is clicked (next or previous entry buttons) in order to display previously entered values from the editTextName
        String id = cursor.getString(0);
        String name = cursor.getString(1);
        editTextId.setText(id); // any value previously entered by the user has an id assigned to that specific value
        editTextName.setText(name);

    }

    protected void moveToNextEntry() {// called when next entry button is clicked
        if (!cursor.isLast())
            cursor.moveToNext();

        showRecords();
    }

    protected void moveToPrevEntry() { // called when previous entry button is clicked
        if (!cursor.isFirst())
            cursor.moveToPrevious();

        showRecords();

    }


    //handles click events for the 2 buttons on the screen
    @Override
    public void onClick(View v) {
        if (v == buttonNext) {
            playSound();
            moveToNextEntry();
        }

        if (v == buttonPrev) {
            playSound();
            moveToPrevEntry();
        }

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

        switch (item.getItemId()) {

            case R.id.action_settings: // Saved Preference shortcut button
                playSound2();

                SavedPreference();


                return true;

            case R.id.action_settings2: // Map shortcut button
                playSound2();

                Map();


                return true;

            case R.id.action_settings3: // Weather Forecast shortcut button
                playSound2();

                WeatherForecast();    //


                return true;

            case R.id.action_settings4: // Piechart shortcut button
                playSound2();

                PieChart();


                return true;

            case R.id.action_settings5: // location Details shortcut button
                playSound2();

                LocationDetails();


                return true;

            case R.id.action_settings6: // exit button
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
        Intent i = new Intent(WriteToDbRecords.this, RssInput.class);
        startActivity(i); // called from the actionbar
        //shortcut to the saved preference page
    }

    private void Map() {
        Intent i = new Intent(WriteToDbRecords.this, mcMapActivity.class);
        startActivity(i); // called from the actionbar
        //shortcut to the map page
    }

    private void WeatherForecast() {
        Intent i = new Intent(WriteToDbRecords.this, RssInput.class);
        startActivity(i); // called from the actionbar
        //shortcut to the Weather forecast spinner input page
    }

    private void PieChart() {
        Intent i = new Intent(WriteToDbRecords.this, PiechartActivity.class);
        startActivity(i); // called from the actionbar
        //shortcut to the Piechart and accelerometer page
    }

    private void LocationDetails() {
        Intent i = new Intent(WriteToDbRecords.this, DatabaseActivity.class);
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
                WriteToDbRecords.this.finish(); // if option 1 is selected application closing
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

