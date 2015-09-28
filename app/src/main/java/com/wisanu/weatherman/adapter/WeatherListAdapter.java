package com.wisanu.weatherman.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wisanu.weatherman.R;
import com.wisanu.weatherman.data.WeatherData;
import com.wisanu.weatherman.utils.ImageLoader;

import java.util.List;

public class WeatherListAdapter extends BaseAdapter {

    private LayoutInflater mLayoutInflater;
    private List<WeatherData> mData;
    private ImageLoader mImageLoader;

    public WeatherListAdapter(Context context, List<WeatherData> data){
        mData = data;
        mLayoutInflater = LayoutInflater.from(context);
        mImageLoader = new ImageLoader();
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public WeatherData getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        View reusedView;
        ViewHolder holder;

        if (view == null) {
            reusedView = mLayoutInflater.inflate(R.layout.listitem_weather_info, parent, false);
            holder = new ViewHolder();

            // assign references to our view holder
            holder.imageViewWeatherIcon = (ImageView) reusedView.findViewById(R.id.main_weather_image);
            holder.textViewCity = (TextView) reusedView.findViewById(R.id.main_city_text);
            holder.textViewTemperature = (TextView) reusedView.findViewById(R.id.main_temperature_text);
            holder.textViewWeatherDesc = (TextView) reusedView.findViewById(R.id.main_weather_description_text);
            holder.textViewFeelsLike = (TextView) reusedView.findViewById(R.id.main_real_feel_text);
            holder.textViewPrec = (TextView) reusedView.findViewById(R.id.main_prec_text);
            holder.textViewObserved = (TextView) reusedView.findViewById(R.id.main_observed_text);
            holder.textViewHumidity = (TextView) reusedView.findViewById(R.id.main_humidity_text);
            holder.textViewBarom = (TextView) reusedView.findViewById(R.id.main_barom_text);
            holder.textViewWindSpeed = (TextView) reusedView.findViewById(R.id.main_win_speed_text);
            holder.layoutFavourite = (LinearLayout) reusedView.findViewById(R.id.main_favourite_layout);
            holder.layoutWeatherInfo = (LinearLayout) reusedView.findViewById(R.id.main_weather_info_layout);
            holder.layoutRootCell = (LinearLayout) reusedView.findViewById(R.id.main_root_cell_layout);

            reusedView.setTag(holder);
        } else {
            reusedView = view;
            holder = (ViewHolder) reusedView.getTag();
        }

        WeatherData item = getItem(position);
        holder.textViewCity.setText(item.getCity());

        // assign values only if the information available
        if(item.getTemperature() != null && !item.getTemperature().isEmpty()) {

            holder.layoutWeatherInfo.setVisibility(View.VISIBLE);
            holder.textViewTemperature.setText(item.getTemperature());
            holder.textViewWeatherDesc.setText(item.getWeatherDesc());
            holder.textViewFeelsLike.setText(item.getFeelsLike());
            holder.textViewPrec.setText(item.getPrec());
            holder.textViewObserved.setText(item.getObserved());

            // show information regarding to favourite status
            if (item.isFavourite()) {
                holder.layoutRootCell.setBackgroundColor(Color.GRAY);
                holder.layoutFavourite.setVisibility(View.VISIBLE);
                holder.textViewHumidity.setText(item.getHumidity());
                holder.textViewBarom.setText(item.getBarom());
                holder.textViewWindSpeed.setText(item.getWindSpeed());
            } else {
                // hide view if it's not favourite
                holder.layoutFavourite.setVisibility(View.GONE);
                // reset background
                holder.layoutRootCell.setBackgroundColor(Color.TRANSPARENT);
            }
            // load image with the loader
            mImageLoader.displayImage(item.getWeatherIcon(), holder.imageViewWeatherIcon);
        }else{
            // hide view when there is no information to show
            holder.layoutWeatherInfo.setVisibility(View.GONE);
        }
        return reusedView;
    }

    static class ViewHolder{

        ImageView imageViewWeatherIcon;
        TextView textViewCity;
        TextView textViewTemperature;
        TextView textViewWeatherDesc;
        TextView textViewFeelsLike;
        TextView textViewPrec;
        TextView textViewObserved;
        TextView textViewHumidity;
        TextView textViewBarom;
        TextView textViewWindSpeed;

        LinearLayout layoutWeatherInfo;
        LinearLayout layoutFavourite;
        LinearLayout layoutRootCell;
    }
}
