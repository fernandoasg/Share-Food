package com.example.sharefood.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.sharefood.back4app.UserParse;
import com.example.sharefood.entity.User;
import com.example.sharefood.repository.UserRepository;

public class UserViewModel extends AndroidViewModel {

    private UserRepository repository;
    private UserParse userParse;

    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository(application);
        userParse = new UserParse(application);
    }

    public String cadastraNovoUsuario(User user){
        return userParse.createUser(user);
    }
}
