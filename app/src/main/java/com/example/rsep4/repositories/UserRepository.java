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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private UserDAO userDAO;
    private static UserRepository instance;
    private MutableLiveData<UserModel> loggedUser;
    private LiveData<UserModel> loggedUserIfNoInternet;
    private UserRepository(Application application)
    {
        LocalDatabase database = LocalDatabase.getInstance(application);
        userDAO = database.userDAO();
//        loggedUserIfNoInternet = userDAO.getLoggedUser();
        loggedUser = new MutableLiveData<>();
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
    public void insertUser(UserModel userModel)
    {
        new UserRepository.InsertUserAsync(userDAO).execute(userModel);
    }
    public void updateUser(UserModel userModel)
    {
        new UserRepository.UpdateUserAsync(userDAO).execute(userModel);
    }

    private static class InsertUserAsync extends AsyncTask<UserModel, Void, Void> {
        private UserDAO userDAO;
        protected InsertUserAsync(UserDAO userDAO) {
            this.userDAO = userDAO;
        }
        @Override
        protected Void doInBackground(UserModel... userModels){
            userDAO.insert(userModels[0]);
            return null;
        }
    }
    private static class UpdateUserAsync extends AsyncTask<UserModel, Void, Void> {
        private UserDAO userDAO;
        protected UpdateUserAsync(UserDAO userDAO) {
            this.userDAO = userDAO;
        }
        @Override
        protected Void doInBackground(UserModel... userModels){
            userDAO.update(userModels[0]);
            return null;
        }
    }
}
