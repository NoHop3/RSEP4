package com.example.rsep4.network;

import com.example.rsep4.models.UserModel;
import com.example.rsep4.models.WeatherModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIService {

    @GET("/api/v1/weather")
    Call<List<WeatherModel>> getWeatherList();

    @GET("/api/v1/weather/{city}")
    Call<WeatherModel> getWeatherForCity(@Path("city") String city);

    @POST("/api/v1/weather")
    Call<WeatherModel> addWeather(@Body WeatherModel weather);

    @PUT("/api/v1/weather/{city}")
    Call<WeatherModel> updateWeatherForCity(@Path("city") String city, @Body WeatherModel weather);

    @DELETE("/api/v1/weather/{city}")
    Call<WeatherModel> deleteWeatherForCity(@Path("city") String city);

    @POST("/api/v1/users/login")
    Call<UserModel> login(@Body UserModel user);

    @POST("/api/v1/users/register")
    Call<UserModel> register(@Body UserModel user);

    @PUT("/api/v1/users/{username}")
    Call<UserModel> updateUser(@Path("username") String username, @Body UserModel user);
}
