package com.example.rsep4.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.rsep4.models.WeatherModel;

import java.net.HttpCookie;
import java.util.List;

@Dao
public interface WeatherDAO {
    @Insert
    void insert(WeatherModel weatherModel);

    @Update
    void update(WeatherModel weatherModel);

    @Delete
    void delete(WeatherModel weatherModel);


    @Query("DELETE FROM weather_table")
    void deleteAllWeather();

    @Query("SELECT * FROM weather_table")
    LiveData<List<WeatherModel>> getAllWeather();

    @Query("SELECT * FROM weather_table WHERE weather_table.city = :city")
    LiveData<WeatherModel> getWeatherDetails(String city);
}
