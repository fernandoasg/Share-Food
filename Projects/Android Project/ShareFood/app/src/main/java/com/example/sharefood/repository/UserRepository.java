package com.example.sharefood.repository;

import android.app.Application;

import com.example.sharefood.ShareFoodDatabase;

public class UserRepository {

    // TODO CRIAR E ADD AQUI O UserDao

    public UserRepository(Application application){
        ShareFoodDatabase database = ShareFoodDatabase.getInstance(application);
    }
}
