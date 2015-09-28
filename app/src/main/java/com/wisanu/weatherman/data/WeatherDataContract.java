package com.wisanu.weatherman.data;

import android.provider.BaseColumns;

/**
 * Created by wisanu on 9/27/2015.
 */
public abstract class WeatherDataContract implements BaseColumns{

    public static final String TABLE_NAME = "weather";
    public static final String COLUMN_CITY = "city";

    public static final String COLUMN_FEEL_LIKE = "feel_like";
    public static final String COLUMN_OBSERVATION_TIME = "observation_time";
    public static final String COLUMN_TEMPERATURE = "temperature";
    public static final String COLUMN_PRESSURE = "pressure";
    public static final String COLUMN_PRECIP = "precipitation";
    public static final String COLUMN_WIND_SPEED = "wind_speed";
    public static final String COLUMN_HUMIDITY = "humidity";
    public static final String COLUMN_WEATHER_DESCRIPTION = "weather_esc";
    public static final String COLUMN_ICON_URL = "icon_url";
    public static final String COLUMN_FAVOURITE = "favourite";

}
