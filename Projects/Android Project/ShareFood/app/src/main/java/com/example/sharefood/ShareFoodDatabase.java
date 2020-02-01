package com.example.sharefood;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.sharefood.dao.FoodPostDao;
import com.example.sharefood.dao.FoodStoreDao;
import com.example.sharefood.entity.FoodPost;
import com.example.sharefood.entity.FoodStore;

@Database(entities = {FoodPost.class, FoodStore.class}, version = 3)
public abstract class ShareFoodDatabase extends RoomDatabase {

    private static ShareFoodDatabase instance;

    public abstract FoodPostDao foodPostDao();
    public abstract FoodStoreDao foodStoreDao();

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
        private FoodStoreDao foodStoreDao;

        private PopulateDbAsyncTask(ShareFoodDatabase db){
            foodPostDao = db.foodPostDao();
            foodStoreDao = db.foodStoreDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            foodPostDao.insert(new FoodPost("Titulo 1", "Descrição descrição descrioção descrição descrição descrioção",
                    null, "03/01/2020", "Pode ser retirado a tarde", 1, 2, 0, 0));
            foodPostDao.insert(new FoodPost("Titulo 2", "Descrição descrição descrioção descrição descrição descrioção",
                    null, "03/01/2020", "Mande uma mensagem para marcarmos um horário", 1, 2,  0, 0));
            foodPostDao.insert(new FoodPost("Titulo 3", "Descrição descrição descrioção descrição descrição descrioção",
                    null, "03/01/2020", "Entre as 11:00 e 13:00", 1, 2,  0, 0));
            foodPostDao.insert(new FoodPost("Titulo 4", "Descrição descrição descrioção descrição descrição descrioção",
                    null, "03/01/2020", "Deixarei na portaria do edíficio Armando Freire", 1, 2,  0, 0));

            foodStoreDao.insert((new FoodStore("Nome da Loja", "Descrição da loja, descrição da loja, descrição da loja...",
                    "01/02/2020", "A tarde e a noite", 1,2, 0, 0)));

            System.out.println("We're like young volcanoes");
            return null;
        }
    }
}
