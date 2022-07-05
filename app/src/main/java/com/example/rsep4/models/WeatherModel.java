package com.example.rsep4.models;

import com.google.gson.annotations.SerializedName;

import java.sql.Date;

public class WeatherModel {

    private String _id;
    private String city;
    private String country;
    private int temperature;
    private int humidity;
    private int pressure;

    // If the variable we declare is different than the json object we use SerializedName
    @SerializedName("windspeed")
    private int windSpeed;

    private String windDirection;
    private String description;
    private String icon;
    private String date;

    public WeatherModel(String _id, String city, String country, int temperature, int humidity, int pressure, int windSpeed, String windDirection, String description, String icon, String date) {
        this._id = _id;
        this.city = city;
        this.country = country;
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.description = description;
        this.icon = icon;
        this.date = date;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
