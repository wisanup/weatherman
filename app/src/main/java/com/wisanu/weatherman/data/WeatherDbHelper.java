package com.wisanu.weatherman.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WeatherDbHelper extends SQLiteOpenHelper {

    // When change the database schema, we must increment the database version.
    public static final int DATABASE_VERSION = 6;

    // data types
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";

    // create statement for schema definition
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + WeatherDataContract.TABLE_NAME + " (" +
                    WeatherDataContract._ID + INTEGER_TYPE + " PRIMARY KEY," +
                    WeatherDataContract.COLUMN_CITY + TEXT_TYPE + " ," +
                    WeatherDataContract.COLUMN_FAVOURITE + INTEGER_TYPE + " ," +
                    WeatherDataContract.COLUMN_FEEL_LIKE + TEXT_TYPE + " ," +
                    WeatherDataContract.COLUMN_HUMIDITY + TEXT_TYPE + " ," +
                    WeatherDataContract.COLUMN_ICON_URL + TEXT_TYPE + " ," +
                    WeatherDataContract.COLUMN_OBSERVATION_TIME + TEXT_TYPE + " ," +
                    WeatherDataContract.COLUMN_PRECIP + TEXT_TYPE + " ," +
                    WeatherDataContract.COLUMN_PRESSURE + TEXT_TYPE + " ," +
                    WeatherDataContract.COLUMN_TEMPERATURE + TEXT_TYPE + " ," +
                    WeatherDataContract.COLUMN_WEATHER_DESCRIPTION + TEXT_TYPE + " ," +
                    WeatherDataContract.COLUMN_WIND_SPEED + TEXT_TYPE + //" ," +
            " )";
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " +
            WeatherDataContract.TABLE_NAME;

    // database file name
    public static final String DATABASE_NAME = "weather.db";

    public WeatherDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);

        // insert default cities
        db.execSQL("insert into " + WeatherDataContract.TABLE_NAME + "("
                + WeatherDataContract.COLUMN_CITY + ") " +
                "values('Sydney')");
        db.execSQL("insert into " + WeatherDataContract.TABLE_NAME + "("
                + WeatherDataContract.COLUMN_CITY + ") " +
                "values('Melbourne')");
        db.execSQL("insert into " + WeatherDataContract.TABLE_NAME + "("
                + WeatherDataContract.COLUMN_CITY + ") " +
                "values('Brisbane')");
        db.execSQL("insert into " + WeatherDataContract.TABLE_NAME + "("
                + WeatherDataContract.COLUMN_CITY + ") " +
                "values('Adelaide')");
        db.execSQL("insert into " + WeatherDataContract.TABLE_NAME + "("
                + WeatherDataContract.COLUMN_CITY + ") " +
                "values('Perth')");
        db.execSQL("insert into " + WeatherDataContract.TABLE_NAME + "("
                + WeatherDataContract.COLUMN_CITY + ") " +
                "values('Hobart')");
        db.execSQL("insert into " + WeatherDataContract.TABLE_NAME + "("
                + WeatherDataContract.COLUMN_CITY + ") " +
                "values('Darwin')");

    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Upgrade policy is to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}