package com.example.rsep4.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.rsep4.models.WeatherModel;
import com.example.rsep4.repositories.WeatherRepository;

import java.util.List;


public class WeatherViewModel extends AndroidViewModel {

    private WeatherRepository weatherRepository;
    public WeatherViewModel(Application application){
        super(application);
        weatherRepository = WeatherRepository.getInstance(application);
    }

    public LiveData<List<WeatherModel>> getWeatherListObserver() {
        return weatherRepository.getAllWeather();
    }

    public MutableLiveData<List<WeatherModel>> fetchAllWeather(){
        weatherRepository.fetchAllWeather();
        return weatherRepository.getFetchAllWeather();
    }
}