package com.example.rsep4.models;

public class WeatherModel {

    private String _id;
    private String city;
    private String country;
    private int avgTemp;
    private int minTemp;
    private int maxTemp;
    private int humidity;
    private int pressure;
    private int wind;
    private String sunsetTime;
    private String sunriseTime;
    private String description;
    private String picture;
    private String updatedAt;
    private String status;

    public WeatherModel(String city, String country, int avgTemp, int minTemp, int maxTemp, int humidity, int pressure, int wind, String description, String picture, String updatedAt, String status, String sunriseTime, String sunsetTime) {
        this.city = city;
        this.country = country;
        this.avgTemp = avgTemp;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.humidity = humidity;
        this.pressure = pressure;
        this.wind = wind;
        this.sunriseTime = sunriseTime;
        this.sunsetTime = sunsetTime;
        this.description = description;
        this.picture = picture;
        this.updatedAt = updatedAt;
        this.status = status;
    }

    public WeatherModel(String _id, String city, String country, int avgTemp, int minTemp, int maxTemp, int humidity, int pressure, int wind, String description, String picture, String updatedAt, String status, String sunriseTime, String sunsetTime) {
        this(city, country, avgTemp, minTemp, maxTemp, humidity, pressure, wind, description, picture, updatedAt, status, sunriseTime, sunsetTime);
        this._id = _id;
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

    public int getAvgTemp() {
        return avgTemp;
    }

    public void setAvgTemp(int avgTemp) {
        this.avgTemp = avgTemp;
    }

    public int getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(int minTemp) {
        this.minTemp = minTemp;
    }

    public int getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(int maxTemp) {
        this.maxTemp = maxTemp;
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

    public int getWind() {
        return wind;
    }

    public void setWind(int wind) {
        this.wind = wind;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSunsetTime() {
        return sunsetTime;
    }

    public void setSunsetTime(String sunsetTime) {
        this.sunsetTime = sunsetTime;
    }

    public String getSunriseTime() {
        return sunriseTime;
    }

    public void setSunriseTime(String sunriseTime) {
        this.sunriseTime = sunriseTime;
    }
}