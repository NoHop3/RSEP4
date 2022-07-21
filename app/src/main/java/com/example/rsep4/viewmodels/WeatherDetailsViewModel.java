package com.example.rsep4.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rsep4.models.WeatherModel;
import com.example.rsep4.network.APIService;
import com.example.rsep4.network.RetrofitInstance;
import com.example.rsep4.repositories.WeatherRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherDetailsViewModel extends AndroidViewModel {
    private WeatherRepository weatherRepository;
    public WeatherDetailsViewModel(Application application){
        super(application);
        weatherRepository = WeatherRepository.getInstance(application);
    }

    public MutableLiveData<WeatherModel> getWeatherObjectObserver() {
        return weatherRepository.getSelectedWeather();
    }

    public String getCity() {
        return weatherRepository.getCity();
    }

    public void setCity(String city) {
        weatherRepository.setCity(city);
    }

    public void createWeather(WeatherModel weatherToAdd){
        weatherRepository.createWeather(weatherToAdd);
    }

    public void getWeatherDetails(String city){
        weatherRepository.getWeatherDetails(city);
    }

    public void updateWeatherDetails(String city, WeatherModel weatherToUpdate){
        weatherRepository.updateWeatherDetails(city, weatherToUpdate);
    }

    public void deleteWeatherDetails(String city){
        weatherRepository.deleteWeatherDetails(city);
    }
    public void resetDetails(){
        weatherRepository.resetDetails();
    }
}
