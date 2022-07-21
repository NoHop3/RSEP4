package com.example.rsep4.repositories;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.rsep4.database.LocalDatabase;
import com.example.rsep4.database.WeatherDAO;
import com.example.rsep4.models.WeatherModel;
import com.example.rsep4.network.APIService;
import com.example.rsep4.network.RetrofitInstance;
import com.example.rsep4.utils.NetworkCheck;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    private NetworkCheck networkCheck;
    ExecutorService executorService;

    private WeatherRepository(Application application){
        LocalDatabase database = LocalDatabase.getInstance(application);
        weatherDAO = database.weatherDAO();
        allWeather = weatherDAO.getAllWeather();
        fetchAllWeather = new MutableLiveData<>();
        selectedWeather = new MutableLiveData<>();
        networkCheck = new NetworkCheck(application);
        executorService = Executors.newFixedThreadPool(2);
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

    public LiveData<List<WeatherModel>> getAllWeatherFromDb(){
        if(allWeather == null)
        {
            allWeather = weatherDAO.getAllWeather();
        }
        return allWeather;
    }

    public void insert(WeatherModel weatherModel)
    {
        executorService.execute(() -> weatherDAO.insert(weatherModel));
    }
    public void update(WeatherModel weatherModel)
    {
        executorService.execute(() -> weatherDAO.update(weatherModel));
    }
    public void delete(WeatherModel weatherModel)
    {
        executorService.execute(() -> weatherDAO.delete(weatherModel));
    }
    public void deleteAll(WeatherModel weatherModel)
    {
        executorService.execute(() -> weatherDAO.deleteAllWeather());
    }



    public void fetchAllWeather() {
        if(networkCheck.isNetworkAvailable()) // check network connection
        {
            Log.d("debuging", "network is available?");
            APIService apiService = RetrofitInstance.getRetrofitClient().create(APIService.class);
            Call<List<WeatherModel>> call = apiService.getWeatherList();
            call.enqueue(new Callback<List<WeatherModel>>() {
                @Override
                public void onResponse(@NonNull Call<List<WeatherModel>> call, @NonNull Response<List<WeatherModel>> response) {
                    Log.d("response", response.toString());
                    if(response.isSuccessful())
                    {
                        fetchAllWeather.setValue(response.body()); // get rooms from the API
                        assert response.body() != null;
                        for (WeatherModel weather : response.body())
                        {
                            insert(weather); // add the data to the local db
                        }
                        allWeather = weatherDAO.getAllWeather();
                    }
                    else{
                        Log.e("fail fetching weather", response.message());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<WeatherModel>> call, @NonNull Throwable t) {
                    Log.d("response", t.toString());
                    fetchAllWeather.setValue(null);
                }
            });
        }
        else {
            Log.d("debuging", "network not available?");
            Log.d("checking data in db", getAllWeatherFromDb().getValue().toString());
            fetchAllWeather.setValue(getAllWeatherFromDb().getValue()); // get data from the local db
        }
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
        if(networkCheck.isNetworkAvailable())
        {
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
        else {
            selectedWeather.postValue(weatherDAO.getWeatherDetails(city).getValue());
        }
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
}


