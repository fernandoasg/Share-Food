package com.example.sharefood;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.sharefood.dao.FoodPostDao;
import com.example.sharefood.entity.FoodPost;

@Database(entities = {FoodPost.class}, version = 1)
public abstract class ShareFoodDatabase extends RoomDatabase {

    private static ShareFoodDatabase instance;

    public abstract FoodPostDao foodPostDao();

    public static synchronized ShareFoodDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ShareFoodDatabase.class, "share_food_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }
}
