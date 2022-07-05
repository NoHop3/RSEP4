package com.example.rsep4.network;

import com.example.rsep4.models.WeatherModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {

    @GET("/api/v1/weather")
    Call<List<WeatherModel>> getWeatherList();
}
