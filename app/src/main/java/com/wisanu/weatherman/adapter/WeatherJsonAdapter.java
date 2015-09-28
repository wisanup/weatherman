package com.wisanu.weatherman.adapter;

import android.util.Log;

import com.wisanu.weatherman.data.WeatherData;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A helper class for object translation to WeatherData object
 */
public class WeatherJsonAdapter {

    private static final String TAG = WeatherJsonAdapter.class.getSimpleName();

    private static final String FIELD_FEEL_LIKE = "FeelsLikeC";
    private static final String FIELD_OBSERVATION_TIME = "observation_time";
    private static final String FIELD_TEMPERATURE = "temp_C";
    private static final String FIELD_PRESSURE = "pressure";
    private static final String FIELD_PRECIP = "precipMM";
    private static final String FIELD_WIND_SPEED = "windspeedKmph";
    private static final String FIELD_HUMIDITY = "humidity";
    private static final String FIELD_VALUE = "value";

    private static final String NODE_DATA = "data";
    private static final String NODE_CONDITION = "current_condition";
    private static final String NODE_DESCRIPTION = "weatherDesc";
    private static final String NODE_ICON_URL = "weatherIconUrl";

    /**
     * Copy value from JSON object to WeatherData object
     * @param data destination object
     * @param json source object
     */
    public static void updateFromJson(WeatherData data, JSONObject json){
        try {
            JSONObject condition = json.getJSONObject(NODE_DATA).getJSONArray(NODE_CONDITION).getJSONObject(0);
            data.setFeelsLike(condition.getString(FIELD_FEEL_LIKE));
            data.setObserved(condition.getString(FIELD_OBSERVATION_TIME));
            data.setTemperature(condition.getString(FIELD_TEMPERATURE));
            data.setBarom(condition.getString(FIELD_PRESSURE));
            data.setPrec(condition.getString(FIELD_PRECIP));
            data.setWindSpeed(condition.getString(FIELD_WIND_SPEED));
            data.setHumidity(condition.getString(FIELD_HUMIDITY));
            data.setWeatherDesc(condition.getJSONArray(NODE_DESCRIPTION).getJSONObject(0).getString(FIELD_VALUE));
            data.setWeatherIcon(condition.getJSONArray(NODE_ICON_URL).getJSONObject(0).getString(FIELD_VALUE));
        }catch(JSONException e){
            Log.e(TAG, "error during parse JSON " + e.getMessage(), e);
        }
    }
}
