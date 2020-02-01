package com.example.sharefood.viewmodel;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.sharefood.entity.FoodPost;
import com.example.sharefood.entity.FoodStore;
import com.example.sharefood.repository.FoodPostRepository;
import com.example.sharefood.repository.FoodStoreRepository;

import java.util.List;

public class FoodStoreViewModel extends AndroidViewModel {

    private FoodStoreRepository repository;
    private List<FoodStore> allFoodStores;

    public FoodStoreViewModel(@NonNull Application application) {
        super(application);
        repository = new FoodStoreRepository(application);
        allFoodStores = repository.getAllFoodStores();
    }

    public void insert(FoodStore foodStore){
        repository.insert(foodStore);
    }

    public void update(FoodStore foodStore){
        repository.update(foodStore);
    }

    public void delete(FoodStore foodStore){
        repository.delete(foodStore);
    }

    public List<FoodStore> getAllFoodStores(){
        return allFoodStores;
    }
}
