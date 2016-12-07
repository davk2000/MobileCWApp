package com.example.dav.mobilecwapp;


import java.io.Serializable;



public class mcMapData implements Serializable {
    // Declares getters and setters etc.
    private int entryID;
    private String location_Name;
    private String postcode_District;
    private float Latitude;
    private float Longitude;
    private static final long serialVersionUID = 0L;



    public int getEntryID() {
        return entryID;
    }
    public void setEntryID(int entryID) {
        this.entryID = entryID;
    }
    public String getlocation_Name() {
        return location_Name;
    }
    public void setlocation_Name(String location_Name) {
        this.location_Name = location_Name;
    }
    public String getpostcode_District() {
        return postcode_District;
    }
    public void setpostcode_District(String postcode_District) {
        this.postcode_District = postcode_District;
    }

    public float getLatitude()
    {
        return Latitude;
    }
    public void setLatitude(float Lat)
    {
        this.Latitude = Lat;
    }
    public float getLongitude()
    {
        return Longitude;
    }
    public void setLongitude(float fLongitude)
    {
        this.Longitude = fLongitude;
    }
    @Override
    public String toString() {
        String mapData;
        mapData = "mcStarSignsInfo [entryID=" + entryID;
        mapData = ", LocationName=" + location_Name;
        mapData = ", PostcodeDistrict=" + postcode_District;

        mapData = ", Latitude=" + Latitude;
        mapData = ", Longitude=" + Longitude +"]";
        return mapData;
    }


}

