package com.franz.easyweather.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class WRealTimeBean{

    private String text;

    private String code;

    private String temperature;

    private String feels_like;

    private String pressure;

    private String humidity;

    private String visibility;

    private String wind_direction;

    private String wind_direction_degree;

    private String wind_speed;

    private String wind_scale;

    private String clouds;

    private String dewPoint;

    private String last_update;

    private String location;


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getFeelsLike() {
        return feels_like;
    }

    public void setFeelsLike(String feelsLike) {
        this.feels_like = feelsLike;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getWindDirection() {
        return wind_direction;
    }

    public void setWindDirection(String windDirection) {
        this.wind_direction = windDirection;
    }

    public String getWindDirectionDegree() {
        return wind_direction_degree;
    }

    public void setWindDirectionDegree(String windDirectionDegree) {
        this.wind_direction_degree = windDirectionDegree;
    }

    public String getWindSpeed() {
        return wind_speed;
    }

    public void setWindSpeed(String windSpeed) {
        this.wind_speed = windSpeed;
    }

    public String getWindScale() {
        return wind_scale;
    }

    public void setWindScale(String windScale) {
        this.wind_scale = windScale;
    }

    public String getClouds() {
        return clouds;
    }

    public void setClouds(String clouds) {
        this.clouds = clouds;
    }

    public String getDewPoint() {
        return dewPoint;
    }

    public void setDewPoint(String dewPoint) {
        this.dewPoint = dewPoint;
    }

    public String getLast_update() {
        return last_update;
    }

    public void setLast_update(String last_update) {
        this.last_update = last_update;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
