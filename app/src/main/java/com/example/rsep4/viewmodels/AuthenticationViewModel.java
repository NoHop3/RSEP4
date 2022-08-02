package com.example.rsep4.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.rsep4.models.UserModel;
import com.example.rsep4.repositories.UserRepository;

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
