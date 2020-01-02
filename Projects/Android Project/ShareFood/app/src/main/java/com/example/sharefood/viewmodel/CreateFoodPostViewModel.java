package com.example.sharefood.viewmodel;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.sharefood.entity.FoodPost;
import com.example.sharefood.repository.FoodPostRepository;

public class CreateFoodPostViewModel extends AndroidViewModel {

    private FoodPostRepository repository;

    public CreateFoodPostViewModel(@NonNull Application application) {
        super(application);
        repository = new FoodPostRepository(application);
    }

    public void insert(FoodPost foodPost){
        repository.insert(foodPost);
    }

    public void update(FoodPost foodPost){
        repository.update(foodPost);
    }

}
