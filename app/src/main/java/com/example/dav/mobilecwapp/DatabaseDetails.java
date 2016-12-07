package com.example.dav.mobilecwapp;



public class DatabaseDetails {

    // class providing getters and setters for location details within databaseactivity class
    // used for database called databaseinfo.s3db
    public int id;
    public String Location_name = "";
    public String Postcode_District = "";
    public String Coordinates = "";


    public int getID() {
        return this.id;
    }

    public void setID(int id) {
        // TODO Auto-generated method stub
        this.id = id;
    }

    public String getPostcode_District() {
        return Postcode_District;
    }

    public void setPostcode_District(String Postcode_District) {
        this.Postcode_District = Postcode_District;
    }

    public String getCoordinates() {
        return Coordinates;
    }

    public void setCoordinates(String Coordinates) {
        this.Coordinates = Coordinates;
    }

    public String getLocation_name() {
        return Location_name;
    }

    public void setLocation_name(String Location_name) {
        this.Location_name = Location_name;
    }

    // constructor
    public DatabaseDetails(int id, String Location_name, String Postcode_District, String Coordinates) {
        this.id = id; // instantiates object for use within Database Activity class
        this.Location_name = Location_name;
        this.Postcode_District = Postcode_District;
        this.Coordinates = Coordinates;
    }

    public DatabaseDetails() {

    }

}
