package com.example.sharefood;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.sharefood.dao.FoodPostDao;
import com.example.sharefood.entity.FoodPost;

@Database(entities = {FoodPost.class}, version = 2)
public abstract class ShareFoodDatabase extends RoomDatabase {

    private static ShareFoodDatabase instance;

    public abstract FoodPostDao foodPostDao();

    public static synchronized ShareFoodDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ShareFoodDatabase.class, "share_food_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }

        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{
        private FoodPostDao foodPostDao;

        private PopulateDbAsyncTask(ShareFoodDatabase db){
            foodPostDao = db.foodPostDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            foodPostDao.insert(new FoodPost("Arroz", "Arroz gostosinho",
                    null, 0, 0));
            foodPostDao.insert(new FoodPost("Arroz", "Arroz gostosinho",
                    null, 0, 0));
            foodPostDao.insert(new FoodPost("Arroz", "Arroz gostosinho",
                    null, 0, 0));
            foodPostDao.insert(new FoodPost("Arroz", "Arroz gostosinho",
                    null, 0, 0));

            return null;
        }
    }
}
