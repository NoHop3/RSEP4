package com.example.rsep4.viewmodels;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rsep4.models.WeatherModel;
import com.example.rsep4.network.APIService;
import com.example.rsep4.network.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherViewModel extends ViewModel {

    private MutableLiveData<List<WeatherModel>> weatherList;
    public WeatherViewModel(){
        weatherList = new MutableLiveData<>();
    }

    public MutableLiveData<List<WeatherModel>> getWeatherListObserver() {
        return weatherList;
    }

    public void getAllWeather(){
        APIService apiService = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<List<WeatherModel>> call = apiService.getWeatherList();
        call.enqueue(new Callback<List<WeatherModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<WeatherModel>> call, @NonNull Response<List<WeatherModel>> response) {
                Log.d("response", response.toString());
                weatherList.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<WeatherModel>> call, @NonNull Throwable t) {
                Log.d("response", t.toString());

                weatherList.postValue(null);
            }
        });
    }
}