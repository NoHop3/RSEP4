package com.example.rsep4.viewmodels;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rsep4.models.UserModel;
import com.example.rsep4.network.APIService;
import com.example.rsep4.network.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthenticationViewModel extends ViewModel {
    private MutableLiveData<UserModel> user;
    public AuthenticationViewModel(){
        user = new MutableLiveData<>();
    }

    public MutableLiveData<UserModel> getUserObserver() {
        return user;
    }

    public void login(UserModel userToLogin){
        APIService apiService = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<UserModel> call = apiService.login(userToLogin);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(@NonNull Call<UserModel> call, @NonNull Response<UserModel> response) {
                Log.d("login", response.toString());
                user.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<UserModel> call, @NonNull Throwable t) {
                Log.e("login", t.toString());
                user.postValue(null);
            }
        });
    }

    public void register(UserModel userToLogin){
        APIService apiService = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<UserModel> call = apiService.register(userToLogin);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(@NonNull Call<UserModel> call, @NonNull Response<UserModel> response) {
                Log.d("register", response.toString());
                user.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<UserModel> call, @NonNull Throwable t) {
                Log.e("register", t.toString());
                user.postValue(null);
            }
        });
    }

    public void updateUser(String username, UserModel userToLogin){
        APIService apiService = RetrofitInstance.getRetrofitClient().create(APIService.class);
        Call<UserModel> call = apiService.updateUser(username, userToLogin);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(@NonNull Call<UserModel> call, @NonNull Response<UserModel> response) {
                Log.d("updateUser", response.toString());
                user.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<UserModel> call, @NonNull Throwable t) {
                Log.e("updateUser", t.toString());
                user.postValue(null);
            }
        });
    }

}
