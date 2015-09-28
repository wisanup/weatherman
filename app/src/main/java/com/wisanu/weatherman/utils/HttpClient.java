package com.wisanu.weatherman.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClient {

    private static final String TAG = HttpClient.class.getSimpleName();

    /**
     * Get data from remote service as JSON object
     * @param url the url of remote service
     * @return JSONObject wrapping response from remote, null if network error or invalid JSON data
     */
    public JSONObject getJSON(String url){
        HttpURLConnection connection = null;
        try {
            Log.i(TAG, "requesting " + url);
            connection = (HttpURLConnection) new URL(url).openConnection();
            int responseCode = connection.getResponseCode();
            if(responseCode == 200){
                // return result only server return HTTP OK
                Log.i(TAG, "request OK");
                return new JSONObject(readStream(connection.getInputStream()));
            }else{
                Log.e(TAG, "error got code " + responseCode + ", message "
                        + connection.getResponseMessage());
            }
        } catch (JSONException e) {
            Log.e(TAG, "error while parsing JSON " + e.getMessage(), e);
        } catch (IOException e) {
            Log.e(TAG, "error while connecting to remote host " + e.getMessage(), e);
        } finally {
            if(connection != null)
                connection.disconnect();
        }
        return null;
    }

    /**
     * Get data from remote service as Bitmap object
     * @param url the url of remote service
     * @return Bitmap response from remote, null if network error
     */
    public Bitmap getImage(final String url) {

        HttpURLConnection connection = null;
        try {
            Log.i(TAG, "requesting " + url);
            connection = (HttpURLConnection) new URL(url).openConnection();
            int responseCode = connection.getResponseCode();
            if(responseCode == 200){
                // return result only server return HTTP OK
                Log.i(TAG, "request OK");
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 1;
                return BitmapFactory.decodeStream(connection.getInputStream(), null, options);
            }else{
                Log.e(TAG, "error got code " + responseCode + ", message "
                        + connection.getResponseMessage());
            }
        } catch (IOException e) {
            Log.e(TAG, "error while connecting to remote host " + e.getMessage(), e);
        } finally {
            if(connection != null)
                connection.disconnect();
        }

        return null;
    }

    /*
     * get stream from remote service
     */
    private InputStream getStream(String url){
        HttpURLConnection connection = null;
        try {
            Log.i(TAG, "requesting " + url);
            connection = (HttpURLConnection) new URL(url).openConnection();
            int responseCode = connection.getResponseCode();
            if(responseCode == 200){
                // return result only server return HTTP OK
                Log.i(TAG, "request OK");
                return connection.getInputStream();
            }else{
                Log.e(TAG, "error got code " + responseCode + ", message "
                        + connection.getResponseMessage());
            }
        } catch (IOException e) {
            Log.e(TAG, "error while connecting to remote host " + e.getMessage(), e);
        } finally {
            if(connection != null)
                connection.disconnect();
        }
        return null;
    }

    /*
     * covert InputStream to String
     */
    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuilder response = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            Log.e(TAG, "error while reading stream " + e.getMessage(), e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }
}
