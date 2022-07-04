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
    private Date date;


}
