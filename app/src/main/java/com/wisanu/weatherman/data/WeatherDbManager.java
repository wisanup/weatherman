package com.wisanu.weatherman.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wisanu on 9/27/2015.
 */
public class WeatherDbManager {

    private WeatherDbHelper mDbHelper;

    public WeatherDbManager(Context context){
        mDbHelper = new WeatherDbHelper(context);
    }

    /**
     * Retrieve all weather in local SQLite database
     * @return a list of weather objects
     */
    public List<WeatherData> getAllWeather(){
        List<WeatherData> weatherDataList = new ArrayList<>();
        // Select all Query
        String selectQuery = "SELECT  * FROM " + WeatherDataContract.TABLE_NAME +
                " ORDER BY " + WeatherDataContract._ID;

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        db.query(true,
                WeatherDataContract.TABLE_NAME,
                new String[]{
                    WeatherDataContract.COLUMN_CITY,
                    WeatherDataContract.COLUMN_FEEL_LIKE,
                    WeatherDataContract.COLUMN_OBSERVATION_TIME,
                    WeatherDataContract.COLUMN_TEMPERATURE,
                    WeatherDataContract.COLUMN_PRESSURE,
                    WeatherDataContract.COLUMN_PRECIP,
                    WeatherDataContract.COLUMN_WIND_SPEED,
                    WeatherDataContract.COLUMN_HUMIDITY,
                    WeatherDataContract.COLUMN_WEATHER_DESCRIPTION,
                    WeatherDataContract.COLUMN_ICON_URL,
                    WeatherDataContract.COLUMN_FAVOURITE },
                    null, null, null, null, null, null
                );
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                WeatherData data = new WeatherData(
                    cursor.getString(cursor.getColumnIndex(WeatherDataContract.COLUMN_CITY)),
                    cursor.getString(cursor.getColumnIndex(WeatherDataContract.COLUMN_FEEL_LIKE)),
                    cursor.getString(cursor.getColumnIndex(WeatherDataContract.COLUMN_OBSERVATION_TIME)),
                    cursor.getString(cursor.getColumnIndex(WeatherDataContract.COLUMN_TEMPERATURE)),
                    cursor.getString(cursor.getColumnIndex(WeatherDataContract.COLUMN_PRESSURE)),
                    cursor.getString(cursor.getColumnIndex(WeatherDataContract.COLUMN_PRECIP)),
                    cursor.getString(cursor.getColumnIndex(WeatherDataContract.COLUMN_WIND_SPEED)),
                    cursor.getString(cursor.getColumnIndex(WeatherDataContract.COLUMN_HUMIDITY)),
                    cursor.getString(cursor.getColumnIndex(WeatherDataContract.COLUMN_WEATHER_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndex(WeatherDataContract.COLUMN_ICON_URL)),
                    cursor.getInt(cursor.getColumnIndex(WeatherDataContract.COLUMN_FAVOURITE)) == 1
                );
                // add to the result list
                weatherDataList.add(data);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return weatherDataList;
    }

    /**
     * Update weather object to local SQLite database
     * @param weather the object to update to database
     * @return true if there is a row updated, false otherwise
     */
    public boolean updateWeather(WeatherData weather){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // New values for existing row
        ContentValues values = new ContentValues();
        values.put(WeatherDataContract.COLUMN_FAVOURITE, weather.isFavourite() ? 1 : 0);
        values.put(WeatherDataContract.COLUMN_FEEL_LIKE, weather.getFeelsLike());
        values.put(WeatherDataContract.COLUMN_HUMIDITY, weather.getHumidity());
        values.put(WeatherDataContract.COLUMN_ICON_URL, weather.getWeatherIcon());
        values.put(WeatherDataContract.COLUMN_OBSERVATION_TIME, weather.getObserved());
        values.put(WeatherDataContract.COLUMN_PRECIP, weather.getPrec());
        values.put(WeatherDataContract.COLUMN_PRESSURE, weather.getBarom());
        values.put(WeatherDataContract.COLUMN_TEMPERATURE, weather.getTemperature());
        values.put(WeatherDataContract.COLUMN_WEATHER_DESCRIPTION, weather.getWeatherDesc());
        values.put(WeatherDataContract.COLUMN_WIND_SPEED, weather.getWindSpeed());

        //Set condition of the row to update, based on the city
        String selection = WeatherDataContract.COLUMN_CITY + " = ?";
        String[] selectionArgs = { weather.getCity() };

        // update
        int count = db.update(
                WeatherDataContract.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        return count > 0;
    }
}
