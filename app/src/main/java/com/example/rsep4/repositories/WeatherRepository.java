package com.example.rsep4.repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.rsep4.database.LocalDatabase;
import com.example.rsep4.database.WeatherDAO;
import com.example.rsep4.models.WeatherModel;
import com.example.rsep4.network.APIService;
import com.example.rsep4.network.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherRepository {
    private WeatherDAO weatherDAO;
    private static WeatherRepository instance;
    private LiveData<List<WeatherModel>> allWeather;
    private MutableLiveData<List<WeatherModel>> fetchAllWeather;
    private MutableLiveData<WeatherModel> selectedWeather;
    private String city;

    private WeatherRepository(Application application){
        LocalDatabase database = LocalDatabase.getInstance(application);
        weatherDAO = database.weatherDAO();
        allWeather = weatherDAO.getAllWeather();
        fetchAllWeather = new MutableLiveData<>();
        selectedWeather = new MutableLiveData<>();
        this.city = "";
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public MutableLiveData<List<WeatherModel>> getFetchAllWeather() {
        return fetchAllWeather;
    }
    public MutableLiveData<WeatherModel> getSelectedWeather(){
        return selectedWeather;
    }

    public static synchronized WeatherRepository getInstance(Application application){
        if(instance == null)
        {
            instance = new WeatherRepository(application);
        }
        return instance;
    }

    public LiveData<List<WeatherModel>> getAllWeather(){
        return allWeather;
    }

    public void insert(WeatherModel weatherModel)
    {
        new InsertWeatherAsync(weatherDAO).execute(weatherModel);
    }
    public void update(WeatherModel weatherModel)
    {
        new UpdateWeatherAsync(weatherDAO).execute(weatherModel);
    }
    public void delete(WeatherModel weatherModel)
    {
        new DeleteWeatherAsync(weatherDAO).execute(weatherModel);
    }
    public void deleteAll(WeatherModel weatherModel)
    {
        new DeleteAllWeatherAsync(weatherDAO).execute(weatherModel);
    }

    public void fetchAllWeather() {
        APIService apiService = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<List<WeatherModel>> call = apiService.getWeatherList();
        call.enqueue(new Callback<List<WeatherModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<WeatherModel>> call, @NonNull Response<List<WeatherModel>> response) {
                Log.d("response", response.toString());
                fetchAllWeather.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<WeatherModel>> call, @NonNull Throwable t) {
                Log.d("response", t.toString());
                fetchAllWeather.postValue(null);
            }
        });
    }

    public void createWeather(WeatherModel weatherToAdd) {
        APIService apiService = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<WeatherModel> call = apiService.addWeather(weatherToAdd);
        call.enqueue(new Callback<WeatherModel>() {
            @Override
            public void onResponse(@NonNull Call<WeatherModel> call, @NonNull Response<WeatherModel> response) {
                selectedWeather.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<WeatherModel> call, @NonNull Throwable t) {
                selectedWeather.postValue(null);
            }
        });
    }

    public void getWeatherDetails(String city) {
        APIService apiService = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<WeatherModel> call = apiService.getWeatherForCity(city);

        call.enqueue(new Callback<WeatherModel>() {
            @Override
            public void onResponse(@NonNull Call<WeatherModel> call, @NonNull Response<WeatherModel> response) {
                selectedWeather.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<WeatherModel> call, @NonNull Throwable t) {
                selectedWeather.postValue(null);
            }
        });
    }

    public void updateWeatherDetails(String city, WeatherModel weatherToUpdate) {
        APIService apiService = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<WeatherModel> call = apiService.updateWeatherForCity(city, weatherToUpdate);
        call.enqueue(new Callback<WeatherModel>() {
            @Override
            public void onResponse(@NonNull Call<WeatherModel> call, @NonNull Response<WeatherModel> response) {
                selectedWeather.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<WeatherModel> call, @NonNull Throwable t) {
                selectedWeather.postValue(null);
            }
        });
    }

    public void deleteWeatherDetails(String city) {
        APIService apiService = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<WeatherModel> call = apiService.deleteWeatherForCity(city);
        call.enqueue(new Callback<WeatherModel>() {
            @Override
            public void onResponse(@NonNull Call<WeatherModel> call, @NonNull Response<WeatherModel> response) {
                selectedWeather.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<WeatherModel> call, @NonNull Throwable t) {
                selectedWeather.postValue(null);
            }
        });
    }

    public void resetDetails() {
        setCity("");
        selectedWeather = new MutableLiveData<>();
    }


    private static class InsertWeatherAsync extends AsyncTask<WeatherModel, Void, Void> {
        private WeatherDAO weatherDAO;

        protected InsertWeatherAsync(WeatherDAO weatherDAO){
            this.weatherDAO = weatherDAO;
        }

        @Override
        protected Void doInBackground(WeatherModel... weatherModels){
            weatherDAO.insert(weatherModels[0]);
            return null;
        }
    }

    private static class UpdateWeatherAsync extends AsyncTask<WeatherModel, Void, Void> {
        private WeatherDAO weatherDAO;
        protected UpdateWeatherAsync(WeatherDAO weatherDAO) {
            this.weatherDAO = weatherDAO;
        }
        @Override
        protected Void doInBackground(WeatherModel... weatherModels){
            weatherDAO.update(weatherModels[0]);
            return null;
        }
    }

    private static class DeleteWeatherAsync extends AsyncTask<WeatherModel, Void, Void> {
        private WeatherDAO weatherDAO;
        protected DeleteWeatherAsync(WeatherDAO weatherDAO) {
            this.weatherDAO = weatherDAO;
        }
        @Override
        protected Void doInBackground(WeatherModel... weatherModels){
            weatherDAO.delete(weatherModels[0]);
            return null;
        }
    }

    private static class DeleteAllWeatherAsync extends AsyncTask<WeatherModel, Void, Void> {
        private WeatherDAO weatherDAO;
        protected DeleteAllWeatherAsync(WeatherDAO weatherDAO) {
            this.weatherDAO = weatherDAO;
        }
        @Override
        protected Void doInBackground(WeatherModel... weatherModels){
            weatherDAO.deleteAllWeather();
            return null;
        }
    }
}


