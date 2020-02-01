package com.example.sharefood.repository;

import android.app.Application;
import android.os.AsyncTask;

import com.example.sharefood.ShareFoodDatabase;
import com.example.sharefood.dao.FoodPostDao;
import com.example.sharefood.dao.FoodStoreDao;
import com.example.sharefood.entity.FoodPost;
import com.example.sharefood.entity.FoodStore;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class FoodStoreRepository {

    private FoodStoreDao foodStoreDao;
    private List<FoodStore> allFoodStores;

    public FoodStoreRepository(Application application){
        ShareFoodDatabase database = ShareFoodDatabase.getInstance(application);
        foodStoreDao = database.foodStoreDao();
        System.out.println("Eita preula");
    }

    public void insert(FoodStore foodStore){
        new InsertFoodStoreAsyncTask(foodStoreDao).execute(foodStore);
    }

    public void update(FoodStore foodStore){
        new UpdateFoodStoreAsyncTask(foodStoreDao).execute(foodStore);
    }

    public void delete(FoodStore foodStore){
        new DeleteFoodStoreAsyncTask(foodStoreDao).execute(foodStore);
    }

    public List<FoodStore> getAllFoodStores(){
        try {
            return new GetAllFoodStoresTask(foodStoreDao).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class InsertFoodStoreAsyncTask extends AsyncTask<FoodStore, Void, Void>{

        private FoodStoreDao foodStoreDao;
        private InsertFoodStoreAsyncTask(FoodStoreDao foodStoreDao){
            this.foodStoreDao = foodStoreDao;
        }

        @Override
        protected Void doInBackground(FoodStore... foodStores) {
            foodStoreDao.insert(foodStores[0]);
            return null;
        }
    }

    private static class UpdateFoodStoreAsyncTask extends AsyncTask<FoodStore, Void, Void>{

        private FoodStoreDao foodStoreDao;
        private UpdateFoodStoreAsyncTask(FoodStoreDao foodStoreDao){
            this.foodStoreDao = foodStoreDao;
        }

        @Override
        protected Void doInBackground(FoodStore... foodStores) {
            foodStoreDao.update(foodStores[0]);
            return null;
        }
    }

    private static class DeleteFoodStoreAsyncTask extends AsyncTask<FoodStore, Void, Void>{

        private FoodStoreDao foodStoreDao;
        private DeleteFoodStoreAsyncTask(FoodStoreDao foodStoreDao){
            this.foodStoreDao = foodStoreDao;
        }

        @Override
        protected Void doInBackground(FoodStore... foodStores) {
            foodStoreDao.delete(foodStores[0]);
            return null;
        }
    }

    private class GetAllFoodStoresTask extends AsyncTask<Void, Void, List<FoodStore>> {

        private FoodStoreDao foodStoreDao;
        private GetAllFoodStoresTask(FoodStoreDao foodStoreDao){
            this.foodStoreDao = foodStoreDao;
        }

        @Override
        protected List<FoodStore> doInBackground(Void... url){
            return foodStoreDao.getAllFoodStores();
        }
    }
}
