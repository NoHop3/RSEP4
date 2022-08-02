package com.example.rsep4.repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.rsep4.database.LocalDatabase;
import com.example.rsep4.database.UserDAO;
import com.example.rsep4.models.UserModel;
import com.example.rsep4.models.WeatherModel;
import com.example.rsep4.network.APIService;
import com.example.rsep4.network.RetrofitInstance;
import com.example.rsep4.utils.NetworkCheck;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private UserDAO userDAO;
    private static UserRepository instance;
    private MutableLiveData<UserModel> loggedUser;
    private LiveData<UserModel> loggedUserIfNoInternet;
    private NetworkCheck networkCheck;
    ExecutorService executorService;
    private UserRepository(Application application)
    {
        LocalDatabase database = LocalDatabase.getInstance(application);
        userDAO = database.userDAO();
        loggedUser = new MutableLiveData<>();
        executorService = Executors.newFixedThreadPool(2);
        networkCheck = new NetworkCheck(application);
    }

    public static synchronized UserRepository getInstance(Application application){
        if(instance == null)
        {
            instance = new UserRepository(application);
        }
        return instance;
    }

    public MutableLiveData<UserModel> getLoggedUser() {
        return loggedUser;
    }

    public LiveData<UserModel> getLoggedUserIfNoInternet() {
        return loggedUserIfNoInternet;
    }

    public void login(UserModel userToLogin) {
        if(networkCheck.isNetworkAvailable()) // check network connection
        {
            APIService apiService = RetrofitInstance.getRetrofitClient().create(APIService.class);
            Call<UserModel> call = apiService.login(userToLogin);
            call.enqueue(new Callback<UserModel>() {
                @Override
                public void onResponse(@NonNull Call<UserModel> call, @NonNull Response<UserModel> response) {
                    Log.d("login", response.toString());
                    loggedUser.postValue(response.body());
                }

                @Override
                public void onFailure(@NonNull Call<UserModel> call, @NonNull Throwable t) {
                    Log.e("login", t.toString());
                    loggedUser.postValue(null);
                }
            });
        }
        else {
            Log.d("debuging", "network not available?");
            getUserFromDb(userToLogin.getUsername()); // get data from the local db
        }
    }

    public void register(UserModel userToRegister) {
        APIService apiService = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<UserModel> call = apiService.register(userToRegister);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(@NonNull Call<UserModel> call, @NonNull Response<UserModel> response) {
                Log.d("register", response.toString());
                loggedUser.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<UserModel> call, @NonNull Throwable t) {
                Log.e("register", t.toString());
                loggedUser.postValue(null);
            }
        });
    }

    public void updateUser(String username, UserModel userToUpdate) {
        APIService apiService = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<UserModel> call = apiService.updateUser(username, userToUpdate);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(@NonNull Call<UserModel> call, @NonNull Response<UserModel> response) {
                Log.d("updateUser", response.toString());
                loggedUser.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<UserModel> call, @NonNull Throwable t) {
                Log.e("updateUser", t.toString());
                loggedUser.postValue(null);
            }
        });
    }
    public void insertUserDb(UserModel userModel)
    {
        executorService.execute(()-> userDAO.insert(userModel));
    }
    public void updateUserDb(UserModel userModel)
    {
        executorService.execute(()-> userDAO.update(userModel));
    }
    public LiveData<UserModel> getUserFromDb(String username)
    {
        loggedUserIfNoInternet = userDAO.getUserFromDb(username);
        if(loggedUserIfNoInternet!=null){
            return loggedUserIfNoInternet;
        }
        return null;
    }
}
