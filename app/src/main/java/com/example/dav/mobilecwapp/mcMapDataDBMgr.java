package com.example.dav.mobilecwapp;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;



public class mcMapDataDBMgr extends SQLiteOpenHelper {

    // This class manages database in order to display additional data from the prepopulated database
    private static final int DB_VER = 1;
    private static final String DB_PATH =
            "/data/data/com.example.dav.mobilecwapp/databases/"; // db directory path
    private static final String DB_NAME = "locationDatabase.s3db"; //db file name
    private static final String TBL_MAPEKFAME = "location"; //database table name
    public static final String COL_ENTRYID = "entryID"; // 1st column of data within database and so forth
    public static final String COL_LOCATIONNAME = "location_Name";
    public static final String COL_POSTCODEDISTRICT = "postcode_District";
    public static final String COL_LATITUDE = "Latitude";
    public static final String COL_LONGITUDE = "Longitude";
    private final Context appContext;
    public mcMapDataDBMgr(Context context, String name,
                          SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.appContext = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) { // Creates an empty database if the not available
        String CREATE_MAPEKFAME_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TBL_MAPEKFAME + "("
                + COL_ENTRYID + " INTEGER PRIMARY KEY," + COL_LOCATIONNAME
                + " TEXT," + COL_POSTCODEDISTRICT + " TEXT," + COL_LATITUDE + " FLOAT" +
                COL_LONGITUDE + " FLOAT" +")";
        db.execSQL(CREATE_MAPEKFAME_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { //database from onCreate method is overwritten if relevant database is acquired
        if(newVersion > oldVersion)
        {
            db.execSQL("DROP TABLE IF EXISTS " + TBL_MAPEKFAME);
            onCreate(db);
        }
    }

    public void dbCreate() throws IOException {
        boolean dbExist = dbCheck();
        if(!dbExist){
            //By calling this method an empty database is created into the default path in order to overwrite that database with our database.

            this.getReadableDatabase();
            try {
                copyDBFromAssets();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }
//

    private boolean dbCheck() { // This method clarifies that the correct database has been requested
        SQLiteDatabase db = null;
        try {
            String dbPath = DB_PATH + DB_NAME;
            db = SQLiteDatabase.openDatabase(dbPath, null,
                    SQLiteDatabase.OPEN_READWRITE);
            db.setLocale(Locale.getDefault());
            db.setVersion(1);
        } catch (SQLiteException e) {
            Log.e("SQLHelper", "Database not Found!");
        }
        if (db != null) {
            db.close();
        }
        return db != null ? true : false;
    }

    private void copyDBFromAssets() throws IOException{
        InputStream dbInput = null;
        OutputStream dbOutput = null;
        String dbFileName = DB_PATH + DB_NAME;
        try {
            dbInput = appContext.getAssets().open(DB_NAME);
            dbOutput = new FileOutputStream(dbFileName);
            //transfer bytes from the dbInput to the dbOutput
            byte[] buffer = new byte[1024];
            int length;
            while ((length = dbInput.read(buffer)) > 0) {
                dbOutput.write(buffer, 0, length);
            }
            //Close the streams
            dbOutput.flush();
            dbOutput.close();
            dbInput.close();
        } catch (IOException e)
        {
            throw new Error("Problems copying DB!");
        }
    }
    public void addaMapEKFameEntry(mcMapData aMapEKFame) {
        ContentValues values = new ContentValues();
        values.put(COL_LOCATIONNAME, aMapEKFame.getlocation_Name());
        values.put(COL_POSTCODEDISTRICT, aMapEKFame.getpostcode_District());

        values.put(COL_LATITUDE, aMapEKFame.getLatitude());
        values.put(COL_LONGITUDE, aMapEKFame.getLongitude());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TBL_MAPEKFAME, null, values);
        db.close();
    }
    public mcMapData getMapEKFameEntry(String aMapEKFameEntry) {
        String query = "Select * FROM " + TBL_MAPEKFAME + " WHERE " + COL_LOCATIONNAME +
                " = \"" + aMapEKFameEntry + "\"";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        mcMapData MapDataEntry = new mcMapData();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            MapDataEntry.setEntryID(Integer.parseInt(cursor.getString(0)));
            MapDataEntry.setlocation_Name(cursor.getString(1));
            MapDataEntry.setpostcode_District(cursor.getString(2));

            MapDataEntry.setLatitude(Float.parseFloat(cursor.getString(3)));
            MapDataEntry.setLatitude(Float.parseFloat(cursor.getString(4)));
            cursor.close();
        } else {
            MapDataEntry = null;
        }
        db.close();
        return MapDataEntry;
    }

    public List<mcMapData> allMapData() // reads data to be displayed from the database when selecting a marker on google maps
    {
        String query = "Select * FROM " + TBL_MAPEKFAME;
        List<mcMapData> mcMapDataList = new ArrayList<mcMapData>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast()==false) {
                mcMapData MapDataEntry = new mcMapData();
                MapDataEntry.setEntryID(Integer.parseInt(cursor.getString(0))); // represents a column of data within database
                MapDataEntry.setlocation_Name(cursor.getString(1));
                MapDataEntry.setpostcode_District(cursor.getString(2));

                MapDataEntry.setLatitude(Float.parseFloat(cursor.getString(3)));
                MapDataEntry.setLongitude(Float.parseFloat(cursor.getString(4)));
                mcMapDataList.add(MapDataEntry);
                cursor.moveToNext();
            }
        } else {
            mcMapDataList.add(null);
        }
        db.close();
        return mcMapDataList;
    }



}

