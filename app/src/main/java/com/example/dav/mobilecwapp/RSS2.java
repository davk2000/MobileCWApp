package com.example.dav.mobilecwapp;



import java.util.List;


import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class RSS2 extends AppCompatActivity implements OnItemClickListener {
    // This class is used when the option 2 selected within the spinner

    ListView listview;
    List<Weather> arrayList; //collects 3 day weather forecast items e.g. Mon, Tues & Wed
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rss_main);

        initComponents();

        if (Connection.isNetworkAvailable(this)) {
            new MyRssReadTask() // executes an instance of the subclass in order to utilise AsyncTask
                    .execute("http://open.live.bbc.co.uk/weather/feeds/en/2650225/3dayforecast.rss"); //Edinburgh Feed
            //"http://open.live.bbc.co.uk/weather/feeds/en/2650225/3dayforecast.rss" edin
            //"http://open.live.bbc.co.uk/weather/feeds/en/2646088/3dayforecast.rss" inver
        }

        fmAboutDialogue = this.getFragmentManager(); // called as a paramter for the about dialog box

    }

    private void initComponents() { // This called in order to display listview with the option to select an item
        listview = (ListView) findViewById(android.R.id.list);
        listview.setOnItemClickListener(this);

    }

    class MyRssReadTask extends AsyncTask<String, Void, Void> {
        ProgressDialog waitingDialog;

        @Override
        protected void onPreExecute() { //displays dialog which appears before the activity starts for the RSS
            waitingDialog = new ProgressDialog(RSS2.this);
            waitingDialog.setMessage("Loading Data, Please Wait...");
            waitingDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... urls) {

            arrayList = new RSSParser().getData(urls[0]); // creates an instance the document parser in order to use loop for collecting all the necessary tags

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            waitingDialog.dismiss();
            setDataToListView();
            super.onPostExecute(result);
        }
    }

    protected void setDataToListView() { // Method is called onPostExecute
        if (null != arrayList) {
            WeatherAdapter weatherAdapter = new WeatherAdapter(
                    RSS2.this, R.layout.rss_row, arrayList);
            //This custom adapter ensure that a row of data collaborates with the listview instead of just the listview itself

            listview.setAdapter(weatherAdapter);
        }

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        //When user clicks on item within the list view description tag is captured and sent to the next screen called Weather Description
        final Weather objBean = (Weather) arrayList.get(position);

        Intent intent = new Intent(RSS2.this,
                WeatherDetail2.class);

        intent.putExtra("description", objBean.getDescription());
        startActivity(intent); // intent is used to navigate to the next screen

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



        switch (item.getItemId()) { // action bar calls for each button in the settings, methods are called from the bottom of the page

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
        Intent i = new Intent(RSS2.this, RssInput.class);
        startActivity(i); // called from the actionbar
        //shortcut to the saved preference page
    }

    private void Map() {
        Intent i = new Intent(RSS2.this, mcMapActivity.class);
        startActivity(i); // called from the actionbar
        //shortcut to the map page
    }

    private void WeatherForecast() {
        Intent i = new Intent(RSS2.this, RssInput.class);
        startActivity(i); // called from the actionbar
        //shortcut to the Weather forecast spinner input page
    }

    private void PieChart() {
        Intent i = new Intent(RSS2.this, PiechartActivity.class);
        startActivity(i); // called from the actionbar
        //shortcut to the Piechart and accelerometer page
    }

    private void LocationDetails() {
        Intent i = new Intent(RSS2.this, DatabaseActivity.class);
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
                RSS2.this.finish(); // if option 1 is selected application closing
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

