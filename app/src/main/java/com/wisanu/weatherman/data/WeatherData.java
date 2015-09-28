package com.wisanu.weatherman.data;

/**
 * POJO object representing weather information
 */
public class WeatherData {

    private String weatherIcon;
    private String city;
    private String temperature;
    private String weatherDesc;
    private String feelsLike;
    private String prec;
    private String observed;
    private String humidity;
    private String barom;
    private String windSpeed;
    private boolean favourite;

    public WeatherData(String city,
                       String feelsLike,
                       String observed,
                       String temperature,
                       String barom,
                       String prec,
                       String windSpeed,
                       String humidity,
                       String weatherDesc,
                       String weatherIcon,
                       boolean favourite) {

        this.weatherIcon = weatherIcon;
        this.city = city;
        this.temperature = temperature;
        this.weatherDesc = weatherDesc;
        this.feelsLike = feelsLike;
        this.prec = prec;
        this.observed = observed;
        this.humidity = humidity;
        this.barom = barom;
        this.windSpeed = windSpeed;
        this.favourite = favourite;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }

    public void setWeatherIcon(String weatherIcon) {
        this.weatherIcon = weatherIcon;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getWeatherDesc() {
        return weatherDesc;
    }

    public void setWeatherDesc(String weatherDesc) {
        this.weatherDesc = weatherDesc;
    }

    public String getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(String feelsLike) {
        this.feelsLike = feelsLike;
    }

    public String getPrec() {
        return prec;
    }

    public void setPrec(String prec) {
        this.prec = prec;
    }

    public String getObserved() {
        return observed;
    }

    public void setObserved(String observed) {
        this.observed = observed;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getBarom() {
        return barom;
    }

    public void setBarom(String barom) {
        this.barom = barom;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean faourite) {
        this.favourite = faourite;
    }

}
