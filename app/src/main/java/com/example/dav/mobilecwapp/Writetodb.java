package com.example.dav.mobilecwapp;


import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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


public class Writetodb extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextName; // edit text for users to input values
    private Button btnsub; // submit button
    private Button btnacc; // access entries button

    private SQLiteDatabase db; // variable use to create and insert SQL database queries

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
        setContentView(R.layout.write_to_db);

        createDatabase();

        editTextName = (EditText) findViewById(R.id.editTextName);


        btnsub = (Button) findViewById(R.id.btnAdd);
        btnacc = (Button) findViewById(R.id.btnView);

        btnsub.setOnClickListener(this);
        btnacc.setOnClickListener(this);

        fmAboutDialogue = this.getFragmentManager(); // called as a paramter for the about dialog box
    }


    protected void createDatabase(){ // When page first opens the method is called in order to create empty database
        db=openOrCreateDatabase("PersonDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS persons(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name VARCHAR);");
    }

    protected void insertIntoDB(){ // called from onClick method
        String name = editTextName.getText().toString().trim(); // captures value entered by the user in the edit text

        if(name.equals("")){  // displays message to user if they select submit but a value isn't entered
            Toast.makeText(getApplicationContext(),"Please enter a value", Toast.LENGTH_LONG).show();
            return;
        }

        String query = "INSERT INTO persons (name) VALUES('"+name+"');"; //adds value to database if user has typed in a value
        db.execSQL(query);
        Toast.makeText(getApplicationContext(),"Administrator will be notified of your request", Toast.LENGTH_LONG).show();
        //This would eventually send an email to the administrator, however code was too complex to implement
    }





    private void showPeoples(){ // called from onClick method
        Intent intent = new Intent(this,WriteToDbRecords.class);
        startActivity(intent); // takes user to the next screen for displaying previously entered values
        finish();
    }

    @Override
    public void onClick(View v) {
        if(v == btnsub){ // method is called when user selects the submit button
            insertIntoDB();

        }
        if(v==btnacc){ // method is called when user selects "access entries"
            showPeoples();
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
                return true; // displays about dialog box
            default:

                return super.onOptionsItemSelected(item);
        }


    }

    private void SavedPreference() {
        Intent i = new Intent(Writetodb.this, RssInput.class);
        startActivity(i); // called from the actionbar
        //shortcut to the saved preference page
    }

    private void Map() {
        Intent i = new Intent(Writetodb.this, mcMapActivity.class);
        startActivity(i); // called from the actionbar
        //shortcut to the map page
    }

    private void WeatherForecast() {
        Intent i = new Intent(Writetodb.this, RssInput.class);
        startActivity(i); // called from the actionbar
        //shortcut to the Weather forecast spinner input page
    }

    private void PieChart() {
        Intent i = new Intent(Writetodb.this, PiechartActivity.class);
        startActivity(i); // called from the actionbar
        //shortcut to the Piechart and accelerometer page
    }

    private void LocationDetails() {
        Intent i = new Intent(Writetodb.this, DatabaseActivity.class);
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
                Writetodb.this.finish(); // if option 1 is selected application closing
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
