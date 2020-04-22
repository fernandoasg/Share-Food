package com.example.sharefood.viewmodel;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.sharefood.entity.FoodPost;
import com.example.sharefood.repository.FoodPostRepository;

import java.util.List;

public class FoodPostViewModel extends AndroidViewModel {

    private FoodPostRepository repository;
    private List<FoodPost> allFoodPosts;

    public FoodPostViewModel(@NonNull Application application) {
        super(application);
        repository = new FoodPostRepository(application);
        allFoodPosts = repository.getAllFoodPosts();
    }

    public void insert(FoodPost foodPost){
        repository.insert(foodPost);
        allFoodPosts = repository.getAllFoodPosts();
    }

    public void update(FoodPost foodPost){
        repository.update(foodPost);
    }

    public void delete(FoodPost foodPost){
        repository.delete(foodPost);
    }

    public List<FoodPost> getAllFoodPosts(){
        return allFoodPosts;
    }

    public int getAllFoodPostsAmount(){
        return allFoodPosts.size();
    }
}
