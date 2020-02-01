package com.example.sharefood.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.sharefood.entity.FoodPost;
import com.example.sharefood.entity.FoodStore;

import java.util.List;

@Dao
public interface FoodStoreDao {

    @Insert
    void insert(FoodStore foodStore);

    @Update
    void update(FoodStore foodStore);

    @Delete
    void delete(FoodStore foodStore);

    @Query("SELECT * FROM food_store_table ORDER BY id DESC")
    List<FoodStore> getAllFoodStores();
}
