package com.wisanu.weatherman;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.wisanu.weatherman.adapter.WeatherJsonAdapter;
import com.wisanu.weatherman.adapter.WeatherListAdapter;
import com.wisanu.weatherman.data.WeatherData;
import com.wisanu.weatherman.data.WeatherDbManager;
import com.wisanu.weatherman.utils.HttpClient;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String PREFS = MainActivity.class.getSimpleName();
    private static final String SETTING_INIT_DATA = "data_initial";

    //refresh every 15 seconds
    private static final int REFRESH_INTERVAL = 10 * 1000;

    //for initial data loading check
    private boolean mIsInitialDataLoaded;
    private SharedPreferences mSetting;

    //http client
    private HttpClient mHttpClient;
    private Handler mHandler = new Handler();

    //core data of this application
    //containing weather information of interested cities
    private List<WeatherData> mWeatherDataList;

    //list adapter for displaying weather info
    private WeatherListAdapter mWeatherAdapter;

    //Activity lifecycle state
    //setting this flag to false will cause the workers to stop fetching and stop updating data
    private boolean mIsViewActive;

    //SQLite database manager
    private WeatherDbManager mDbManager;

    //swipe refresh layout
    private SwipeRefreshLayout mRefresherLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHttpClient = new HttpClient();

        // init database manager
        mDbManager = new WeatherDbManager(this);

        // put data to activity cache
        mWeatherDataList = new ArrayList<>();
        mWeatherDataList.addAll(mDbManager.getAllWeather());

        // populate list view with the loaded data
        ListView weatherListView = (ListView) findViewById(R.id.main_list);
        mWeatherAdapter = new WeatherListAdapter(this, mWeatherDataList);
        weatherListView.setAdapter(mWeatherAdapter);

        mSetting = getSharedPreferences(PREFS, MODE_PRIVATE);
        mIsInitialDataLoaded = mSetting.getBoolean(SETTING_INIT_DATA, false);
        Log.i(TAG, "data init status:" + mIsInitialDataLoaded);

        //handle click event, the clicked item will toggle the state of favourite city
        weatherListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                WeatherData data = mWeatherDataList.get(i);
                //toggle favourite status
                data.setFavourite(!data.isFavourite());
                // persist to database
                mDbManager.updateWeather(data);
                // update view
                mWeatherAdapter.notifyDataSetChanged();
            }
        });

        mRefresherLayout = (SwipeRefreshLayout) findViewById(R.id.main_swipe_refresh_layout);
        mRefresherLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRefresherLayout.setRefreshing(true);
                reloadWeatherInfo();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mIsViewActive = true;

        // show loader for the first time
        if (!mIsInitialDataLoaded){
            // post new runnable to avoid indicator not show up initially
            mRefresherLayout.post(new Runnable() {
                @Override
                public void run() {
                    mRefresherLayout.setRefreshing(true);
                }
            });
        }

        // start updating data when the app become visible
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                reloadWeatherInfo();
                // keep refreshing unless the view become invisible
                if (mIsViewActive) {
                    mHandler.postDelayed(this, REFRESH_INTERVAL);
                }
            }
        }, 0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mIsViewActive = false;
    }

    private void reloadWeatherInfo(){
          new WeatherInfoLoaderTask().execute(mWeatherDataList.toArray(new WeatherData[mWeatherDataList.size()]));
    }

    private class WeatherInfoLoaderTask extends AsyncTask<WeatherData, Void, Void> {

        // a56794e9186612645ac157bf8f9be - my key
        // 29560c19c9d75ab12c3c101c05faf - key from Mark
        private static final String WEATHER_API_KEY = "29560c19c9d75ab12c3c101c05faf";
        private static final String BASE_URL = "http://api.worldweatheronline.com/free/v2/weather.ashx";
        private static final String REST_ENDPOINT = BASE_URL + "?q=%s&format=JSON&key=%s";

        @Override
        protected Void doInBackground(WeatherData... params) {
            for (WeatherData data : params){
                Log.i(TAG, "loading " + data.getCity());
                JSONObject result = mHttpClient.getJSON(String.format(REST_ENDPOINT, data.getCity(), WEATHER_API_KEY));
                if (result != null){
                    Log.i(TAG, "loaded " + data.getCity());
                    WeatherJsonAdapter.updateFromJson(data, result);
                    MainActivity.this.mDbManager.updateWeather(data);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void obj) {
            if (MainActivity.this.mIsViewActive) {
               // mWeatherAdapter.updateData(result);
                MainActivity.this.mWeatherAdapter.notifyDataSetChanged();
                if (MainActivity.this.mRefresherLayout.isRefreshing()){
                    MainActivity.this.mRefresherLayout.setRefreshing(false);
                }
                if (!mIsInitialDataLoaded){
                    mIsInitialDataLoaded = true;
                    SharedPreferences.Editor editor = mSetting.edit();
                    editor.putBoolean(SETTING_INIT_DATA, true);
                    editor.commit();
                }
            }
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {}
    }

}
