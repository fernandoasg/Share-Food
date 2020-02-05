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
import com.example.sharefood.dao.MessageDao;
import com.example.sharefood.entity.FoodPost;
import com.example.sharefood.entity.FoodProduct;
import com.example.sharefood.entity.FoodStore;
import com.example.sharefood.entity.Message;

@Database(entities = {FoodPost.class, FoodStore.class, FoodProduct.class, Message.class}, version = 5)
public abstract class ShareFoodDatabase extends RoomDatabase {

    private static ShareFoodDatabase instance;

    public abstract FoodPostDao foodPostDao();
    public abstract FoodStoreDao foodStoreDao();
    public abstract FoodProductDao foodProductDao();
    public abstract MessageDao messageDao();

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

        private PopulateDbAsyncTask(ShareFoodDatabase db){
            foodPostDao = db.foodPostDao();
            foodStoreDao = db.foodStoreDao();
            foodProductDao = db.foodProductDao();
            messageDao = db.messageDao();
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

            Message message1 = new Message("Bom dia", "02/02/2020", 0, -1);
            long id = messageDao.insert(message1);
            Message message2 = new Message("Boa noite", "03/02/2020", 0, -1);
            id = messageDao.insert(message2);
            message1 = new Message("Tudo bem?", "03/02/2020", 0, (int)id);
            messageDao.insert(message1);
            message1 = new Message("Oi, eu gostaria de saber se os feijões estão fechados, por favor", "04/02/2020", 0, -1);
            id = messageDao.insert(message1);
            message2 = new Message("Olá. Estão sim!", "04/02/2020", 1, (int)id);
            id = messageDao.insert(message2);
            message1 = new Message("Certo! Muito obrigado!", "04/02/2020", 0, (int)id);
            messageDao.insert(message1);

            return null;
        }
    }
}
