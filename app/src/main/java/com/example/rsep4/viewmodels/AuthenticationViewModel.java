package com.example.rsep4.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rsep4.models.UserModel;
import com.example.rsep4.network.APIService;
import com.example.rsep4.network.RetrofitInstance;
import com.example.rsep4.repositories.UserRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthenticationViewModel extends AndroidViewModel {
    private UserRepository userRepository;
    public AuthenticationViewModel(Application application){
        super(application);
        userRepository = UserRepository.getInstance(application);
    }

    public void login(UserModel userToLogin){
        userRepository.login(userToLogin);
    }

    public void register(UserModel userToRegister){
        userRepository.register(userToRegister);
    }

    public void updateUser(String username, UserModel userToUpdate){
        userRepository.updateUser(username, userToUpdate);
    }
}
