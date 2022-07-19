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

    @GET("weather/{city}")
    Call<WeatherModel> getWeatherForCity(@Path("city") String city);

    @PUT("weather/{city}")
    Call<WeatherModel> updateWeatherForCity(@Path("city") String city, @Body WeatherModel weather);

    @GET("weather")
    Call<List<WeatherModel>> getWeatherList();

    @POST("weather")
    Call<WeatherModel> addWeather(@Body WeatherModel weather);

    @DELETE("weather/{city}")
    Call<WeatherModel> deleteWeatherForCity(@Path("city") String city);

    @POST("users/login")
    Call<UserModel> login(@Body UserModel user);

    @POST("users/register")
    Call<UserModel> register(@Body UserModel user);

    @PUT("users/{username}")
    Call<UserModel> updateUser(@Path("username") String username, @Body UserModel user);
}
