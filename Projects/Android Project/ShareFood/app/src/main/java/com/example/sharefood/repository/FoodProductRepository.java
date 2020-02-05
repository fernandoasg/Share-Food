package com.example.sharefood.repository;

import android.app.Application;
import android.os.AsyncTask;

import com.example.sharefood.ShareFoodDatabase;
import com.example.sharefood.dao.FoodPostDao;
import com.example.sharefood.dao.FoodProductDao;
import com.example.sharefood.entity.FoodPost;
import com.example.sharefood.entity.FoodProduct;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class FoodProductRepository {

    private FoodProductDao foodProductDao;
    private List<FoodProduct> allFoodProducts;

    public FoodProductRepository(Application application){
        ShareFoodDatabase database = ShareFoodDatabase.getInstance(application);
        foodProductDao = database.foodProductDao();
    }

    public void insert(FoodProduct foodProduct){
        new InsertFoodProductAsyncTask(foodProductDao).execute(foodProduct);
    }

    public void update(FoodProduct foodProduct){
        new UpdateFoodProductAsyncTask(foodProductDao).execute(foodProduct);
    }

    public void delete(FoodProduct foodProduct){
        new DeleteFoodProductAsyncTask(foodProductDao).execute(foodProduct);
    }

    public List<FoodProduct> getAllFoodProducts(){
        try {
            return new GetAllFoodProductsTask(foodProductDao).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class InsertFoodProductAsyncTask extends AsyncTask<FoodProduct, Void, Void>{

        private FoodProductDao foodProductDao;
        private InsertFoodProductAsyncTask(FoodProductDao foodProductDao){
            this.foodProductDao = foodProductDao;
        }

        @Override
        protected Void doInBackground(FoodProduct... foodProducts) {
            foodProductDao.insert(foodProducts[0]);
            return null;
        }
    }

    private static class UpdateFoodProductAsyncTask extends AsyncTask<FoodProduct, Void, Void>{

        private FoodProductDao foodProductDao;
        private UpdateFoodProductAsyncTask(FoodProductDao foodProductDao){
            this.foodProductDao = foodProductDao;
        }

        @Override
        protected Void doInBackground(FoodProduct... foodProducts) {
            foodProductDao.update(foodProducts[0]);
            return null;
        }
    }

    private static class DeleteFoodProductAsyncTask extends AsyncTask<FoodProduct, Void, Void>{

        private FoodProductDao foodProductDao;
        private DeleteFoodProductAsyncTask(FoodProductDao foodProductDao){
            this.foodProductDao = foodProductDao;
        }

        @Override
        protected Void doInBackground(FoodProduct... foodProducts) {
            foodProductDao.delete(foodProducts[0]);
            return null;
        }
    }

    private class GetAllFoodProductsTask extends AsyncTask<Void, Void, List<FoodProduct>> {

        private FoodProductDao foodProductDao;
        private GetAllFoodProductsTask(FoodProductDao foodProductDao){
            this.foodProductDao = foodProductDao;
        }

        @Override
        protected List<FoodProduct> doInBackground(Void... url){
            return foodProductDao.getAllFoodProducts();
        }
    }
}
