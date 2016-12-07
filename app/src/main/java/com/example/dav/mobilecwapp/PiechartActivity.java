package com.example.dav.mobilecwapp;



import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;



public class PiechartActivity extends AppCompatActivity implements SensorEventListener {
    // This class produces the Pie Chart diagram and sensor
    List<PieDetailsItem> piedata = new ArrayList<PieDetailsItem>(0);

    FragmentManager fmAboutDialogue;
    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private long lastUpdate = 0; // variable to show start up value shaking device for the first time
    private float last_x, last_y, last_z; // variables each directional movement during motion
    private static final int SHAKE_THRESHOLD = 400; // Use to increase or decrease the sensitivity when shaking device
    String futureList[] = { "Glasgow Population 606,340.", // array containing data displayed in a textview
            "Edinburgh Population 957,670.",
            "Inverness Population 46,870.",
            "Glasgow Population 606,340."

    };


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
        setContentView(R.layout.piechart);

        PieDetailsItem item;
        int maxCount = 0;
        int itemCount = 0;
        int items[] = { 37, 59, 4}; // percentages for each population
        int greenColorValue = Color.parseColor("#00ff00"); // all colors are declared and assigned to color array
        int blueColorValue = Color.parseColor("#0000FF");
        int purpleColorValue = Color.parseColor("#9900cc");
        int redColorValue = Color.parseColor("#ff0000");
        int yellowColorValue = Color.parseColor("#ffff00");
        int colors[] = { greenColorValue, blueColorValue, redColorValue};

        for (int i = 0; i < items.length; i++) {
            itemCount = items[i]; // consists of the 3 item vlaues for each total population
            item = new PieDetailsItem(); // new instance of PieDetailsItem class in order to use item and color variables
            item.count = itemCount; // item array is assigned to count variable

            item.color = colors[i];
            piedata.add(item); // array of items and colors assigned arraylist - ensures relationship between specific color and population value
            maxCount = maxCount + itemCount;
        }
        int size = 1000;
        int BgColor = 0xfffffff;
        Bitmap mBaggroundImage = Bitmap.createBitmap(size, size,
                Bitmap.Config.ARGB_8888); // This variable represents an instance of the pixelated image
        PiechartMgr piechart = new PiechartMgr(this); // creates an instance of the PiechartMgr class
        piechart.setLayoutParams(new LayoutParams(size, size)); // method declared for defining the layout parameters
        piechart.setGeometry(size, size, 10, 10, 10, 10); // sets the overall size of the pie chart based on width, height etc
        piechart.setData(piedata, maxCount); // applies relevant colours and populations to Pie chart
        piechart.invalidate();
        piechart.draw(new Canvas(mBaggroundImage)); // calls the draw method in order to display image on canvas

        ImageView mImageView = new ImageView(this);
        mImageView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
        mImageView.setBackgroundColor(BgColor); // Pie chart object contains the image and the imageview variable displays the image
        mImageView.setImageBitmap(mBaggroundImage); // assigns draw method's image on imageview within xml
        LinearLayout finalLayout = (LinearLayout) findViewById(R.id.pie_container);
        finalLayout.addView(mImageView);

        fmAboutDialogue = this.getFragmentManager(); // about dialog variable

        senSensorManager = (SensorManager)
                getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer =
                senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer,
                SensorManager.SENSOR_DELAY_NORMAL);
        Random randNumber = new Random(); // Random object used to start off the textview sensor
        int iNumber = randNumber.nextInt(3) + 1; // cycles through array of population information
        TextView futureText = (TextView)findViewById(R.id.cb_out_msg);
        futureText.setText(""+futureList[iNumber]); // once this carried out sensor relies on onSensorChange method

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
        Intent i = new Intent(PiechartActivity.this, RssInput.class);
        startActivity(i); // called from the actionbar
        //shortcut to the saved preference page
    }

    private void Map() {
        Intent i = new Intent(PiechartActivity.this, mcMapActivity.class);
        startActivity(i); // called from the actionbar
        //shortcut to the map page
    }

    private void WeatherForecast() {
        Intent i = new Intent(PiechartActivity.this, RssInput.class);
        startActivity(i); // called from the actionbar
        //shortcut to the Weather forecast spinner input page
    }

    private void PieChart() {
        Intent i = new Intent(PiechartActivity.this, PiechartActivity.class);
        startActivity(i); // called from the actionbar
        //shortcut to the Piechart and accelerometer page
    }

    private void LocationDetails() {
        Intent i = new Intent(PiechartActivity.this, DatabaseActivity.class);
        startActivity(i); // called from the actionbar
        //shortcut to the database
    }

    @Override
    public void onSensorChanged(SensorEvent event) { // This method is called when event occurs for the first time during the activity
        Sensor mySensor = event.sensor;             // It determines if the event for changing the textview should change based on the
        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {   // threshold, the amount of time which has passed since the last event
            float x = event.values[0];                           // and the speed of the event that occurred
            float y = event.values[1];
            float z = event.values[2];
            long curTime = System.currentTimeMillis();
            if ((curTime - lastUpdate) > 50) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;
                float speed = Math.abs(x + y + z - last_x - last_y - last_z)/
                        diffTime * 9000;
                if (speed > SHAKE_THRESHOLD) {
                    Random randNumber = new Random();
                    int iNumber = randNumber.nextInt(3) + 1;
                    TextView futureText = (TextView)findViewById(R.id.cb_out_msg);
                    futureText.setText(""+futureList[iNumber]);
                }
                last_x = x;
                last_y = y;
                last_z = z;
            }
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
    @Override
    protected void onPause() { // If activity is idle no event will occur
        super.onPause();
        senSensorManager.unregisterListener(this);
    }
    @Override
    protected void onResume() {
        super.onResume(); // If activity is no longer idle event will continue based on listening for events
        senSensorManager.registerListener(this, senAccelerometer,
                SensorManager.SENSOR_DELAY_NORMAL);
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
                PiechartActivity.this.finish(); // if option 1 is selected application closing
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
