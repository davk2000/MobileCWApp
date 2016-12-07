package com.example.dav.mobilecwapp;


// This is the custom adapter class which establishes the association between individual listview rows and the listview as a whole
// and also ensures that parsed data is inserted into the textviews within the listview
import android.app.Activity;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class WeatherAdapter extends ArrayAdapter<Weather> {

    private Activity activity; //variable used to initiate layout inflater for modifying views
    private List<Weather> items; // stores all row items from parsed RSS
    private Weather weather; //provides access to getters and setters from the Weather class

    public WeatherAdapter(Activity act, int resource, List<Weather> arrayList) {
        super(act, resource, arrayList); // Constructor to instantiate a WeatherAdapter object used in the RSS, RSS2 & RSS3 classes

        this.activity = act;
        this.items = arrayList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.rss_row, null);

            holder = new ViewHolder();
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        if ((items == null) || ((position + 1) > items.size())) // uses an increment to move through the array
            return view;

        weather = items.get(position); // refers to both the arraylist and the method for locating a specific arraylist position e.g. items[0]


        holder.title = (TextView) view.findViewById(R.id.txttitle);


        if (holder.title != null ) {
            holder.title.setText(weather.getTitle()); // ensures data is captured and displayed in the correct textview
        }
        if (holder.description != null ) {
            holder.description.setText(weather.getDescription()); // ensures data is captured and displayed in the correct textview
        }


        return view;
    }

    public class ViewHolder { //subclass containing global variables to be used within the getView method

        public TextView title; // this textview is only available within the rss_row xml
        public TextView description; // this textview is only available in the rss_detail xml

    }

}
