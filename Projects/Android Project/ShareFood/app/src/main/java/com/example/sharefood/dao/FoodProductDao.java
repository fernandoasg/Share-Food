package com.example.sharefood.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.sharefood.entity.FoodProduct;

import java.util.List;

@Dao
public interface FoodProductDao {

    @Insert
    void insert(FoodProduct foodProduct);

    @Update
    void update(FoodProduct foodProduct);

    @Delete
    void delete(FoodProduct foodProduct);

    @Query("SELECT * FROM food_product_table ORDER BY id DESC")
    List<FoodProduct> getAllFoodProducts();
}
