package com.example.sharefood.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.sharefood.entity.FoodPost;

import java.util.List;

@Dao
public interface FoodPostDao {

    @Insert
    void insert(FoodPost foodPost);

    @Update
    void update(FoodPost foodPost);

    @Delete
    void delete(FoodPost foodPost);

    @Query("SELECT * FROM food_post_table ORDER BY id DESC")
    List<FoodPost> getAllFoodPosts();
}
