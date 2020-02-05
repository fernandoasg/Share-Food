package com.example.sharefood.viewmodel;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.sharefood.entity.FoodPost;
import com.example.sharefood.entity.FoodProduct;
import com.example.sharefood.repository.FoodPostRepository;
import com.example.sharefood.repository.FoodProductRepository;

import java.util.List;

public class FoodProductViewModel extends AndroidViewModel {

    private FoodProductRepository repository;
    private List<FoodProduct> allFoodProducts;

    public FoodProductViewModel(@NonNull Application application) {
        super(application);
        repository = new FoodProductRepository(application);
        allFoodProducts = repository.getAllFoodProducts();
    }

    public void insert(FoodProduct foodProduct){
        repository.insert(foodProduct);
    }

    public void update(FoodProduct foodProduct){
        repository.update(foodProduct);
    }

    public void delete(FoodProduct foodProduct){
        repository.delete(foodProduct);
    }

    public List<FoodProduct> getAllFoodProducts(){
        return allFoodProducts;
    }
}
