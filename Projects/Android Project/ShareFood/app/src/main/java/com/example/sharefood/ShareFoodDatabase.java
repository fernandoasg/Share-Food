package com.example.sharefood;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.sharefood.dao.FoodPostDao;
import com.example.sharefood.dao.FoodProductDao;
import com.example.sharefood.dao.FoodStoreDao;
import com.example.sharefood.dao.InstitutionDao;
import com.example.sharefood.dao.MessageDao;
import com.example.sharefood.entity.FoodPost;
import com.example.sharefood.entity.FoodProduct;
import com.example.sharefood.entity.FoodStore;
import com.example.sharefood.entity.Institution;
import com.example.sharefood.entity.Message;

@Database(entities = {FoodPost.class, FoodStore.class, FoodProduct.class, Message.class, Institution.class}, version = 6)
public abstract class ShareFoodDatabase extends RoomDatabase {

    private static ShareFoodDatabase instance;

    public abstract FoodPostDao foodPostDao();
    public abstract FoodStoreDao foodStoreDao();
    public abstract FoodProductDao foodProductDao();
    public abstract MessageDao messageDao();
    public abstract InstitutionDao institutionDao();

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
        private FoodProductDao foodProductDao;
        private MessageDao messageDao;
        private InstitutionDao institutionDao;

        private PopulateDbAsyncTask(ShareFoodDatabase db){
            foodPostDao = db.foodPostDao();
            foodStoreDao = db.foodStoreDao();
            foodProductDao = db.foodProductDao();
            messageDao = db.messageDao();
            institutionDao = db.institutionDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            foodPostDao.insert(new FoodPost("Instituição 1", "Descrição descrição descrioção descrição descrição descrição descrição",
                    null, "03/01/2020", "Oferecemos almoços diariamente", 1, 2, 0, 0));
            foodPostDao.insert(new FoodPost("Instituição 2", "Descrição descrição descrioção descrição descrição descrição descrição",
                    null, "03/01/2020", "Ajudamos animais de estimação", 1, 2,  0, 0));
            foodPostDao.insert(new FoodPost("Instituição 3", "Descrição descrição descrioção descrição descrição descrição descrição",
                    null, "03/01/2020", "Oferecemos almoços diariamente", 1, 2,  0, 0));
            foodPostDao.insert(new FoodPost("Instituição 4", "Descrição descrição descrioção descrição descrição descrição descrição",
                    null, "03/01/2020", "Ajudamos animais de estimação", 1, 2,  0, 0));

            return null;
        }
    }
}
