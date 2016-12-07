package com.example.dav.mobilecwapp;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class SqLiteDbHelper extends SQLiteOpenHelper {

    //Manages Location Details page by accessing pre-populated database
    // Coincides with DatabaseActivity and DatabaseDetails classes

    private static final int DATABASE_VERSION = 1; // variable within the constructor used for an instance

    private static final String DATABASE_NAME = "databaseinfo.s3db"; // Database Name

    private static final String DB_PATH_SUFFIX = "/databases/";// Table name

    static Context context1;

    public SqLiteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        context1 = context;
    }



    public DatabaseDetails Get_WeatherDetails() { // Gets a single row of data (1st row)
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM locationdetails", null);
        if (cursor != null && cursor.moveToFirst()){
            DatabaseDetails dd = new DatabaseDetails(cursor.getInt(0),cursor.getString(1), cursor.getString(2), cursor.getString(3));
            // returns row
            cursor.close();
            db.close();

            return dd;

        }

        return null;
    }


    public DatabaseDetails Get_WeatherDetails2() { // Gets a single row of data (2nd row)
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM locationdetails", null);
        if (cursor != null && cursor.moveToPosition(1)){
            DatabaseDetails dd = new DatabaseDetails(cursor.getInt(0),cursor.getString(1), cursor.getString(2), cursor.getString(3));
            // return row
            cursor.close();
            db.close();

            return dd;

        }

        return null;
    }


    public DatabaseDetails Get_WeatherDetails3() { // Gets a single row of data (3rd row)
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM locationdetails", null);
        if (cursor != null && cursor.moveToLast()){
            DatabaseDetails dd = new DatabaseDetails(cursor.getInt(0),cursor.getString(1), cursor.getString(2), cursor.getString(3));
            // return row
            cursor.close();
            db.close();

            return dd;

        }

        return null;
    }





    public void CopyDataBaseFromAsset() throws IOException{

        InputStream myInput = context1.getAssets().open(DATABASE_NAME);


        String outFileName = getDatabasePath(); // Uses path to the created empty db


        File f = new File(context1.getApplicationInfo().dataDir + DB_PATH_SUFFIX); // if the path doesn't exist, create the path
        if (!f.exists())
            f.mkdir();


        OutputStream myOutput = new FileOutputStream(outFileName); // Open the empty db


        byte[] buffer = new byte[1024]; // transfer bytes from the inputfile to the outputfile
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }


        myOutput.flush(); // Closes the streams
        myOutput.close();
        myInput.close();

    }
    private static String getDatabasePath() { // used as a parameter to open empty database
        return context1.getApplicationInfo().dataDir + DB_PATH_SUFFIX
                + DATABASE_NAME;
    }

    public SQLiteDatabase openDataBase() throws SQLException{ // if acquiring database is successful database is copied from the assets folder
        File dbFile = context1.getDatabasePath(DATABASE_NAME);
        //By calling this method an empty database is created into the default path in order to overwrite that database with our database.
        if (!dbFile.exists()) {
            try {
                CopyDataBaseFromAsset();
                System.out.println("Copying sucess from Assets folder");
            } catch (IOException e) {
                throw new RuntimeException("Error creating source database", e);
            }
        }

        return SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.CREATE_IF_NECESSARY);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }

}
