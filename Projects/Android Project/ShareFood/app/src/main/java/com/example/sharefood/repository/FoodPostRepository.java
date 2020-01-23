package com.example.sharefood.repository;

import android.app.Application;
import android.os.AsyncTask;

import com.example.sharefood.ShareFoodDatabase;
import com.example.sharefood.dao.FoodPostDao;
import com.example.sharefood.entity.FoodPost;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class FoodPostRepository {

    private FoodPostDao foodPostDao;
    private List<FoodPost> allFoodPosts;

    public FoodPostRepository(Application application){
        ShareFoodDatabase database = ShareFoodDatabase.getInstance(application);
        foodPostDao = database.foodPostDao();
        System.out.println("Eita preula");
    }

    public void insert(FoodPost foodPost){
        new InsertFoodPostAsyncTask(foodPostDao).execute(foodPost);
    }

    public void update(FoodPost foodPost){
        new UpdateFoodPostAsyncTask(foodPostDao).execute(foodPost);
    }

    public void delete(FoodPost foodPost){
        new DeleteFoodPostAsyncTask(foodPostDao).execute(foodPost);
    }

    public List<FoodPost> getAllFoodPosts(){
        try {
            return new GetAllFoodPostsTask(foodPostDao).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class InsertFoodPostAsyncTask extends AsyncTask<FoodPost, Void, Void>{

        private FoodPostDao foodPostDao;
        private InsertFoodPostAsyncTask(FoodPostDao foodPostDao){
            this.foodPostDao = foodPostDao;
        }

        @Override
        protected Void doInBackground(FoodPost... foodPosts) {
            foodPostDao.insert(foodPosts[0]);
            return null;
        }
    }

    private static class UpdateFoodPostAsyncTask extends AsyncTask<FoodPost, Void, Void>{

        private FoodPostDao foodPostDao;
        private UpdateFoodPostAsyncTask(FoodPostDao foodPostDao){
            this.foodPostDao = foodPostDao;
        }

        @Override
        protected Void doInBackground(FoodPost... foodPosts) {
            foodPostDao.update(foodPosts[0]);
            return null;
        }
    }

    private static class DeleteFoodPostAsyncTask extends AsyncTask<FoodPost, Void, Void>{

        private FoodPostDao foodPostDao;
        private DeleteFoodPostAsyncTask(FoodPostDao foodPostDao){
            this.foodPostDao = foodPostDao;
        }

        @Override
        protected Void doInBackground(FoodPost... foodPosts) {
            foodPostDao.delete(foodPosts[0]);
            return null;
        }
    }

    private class GetAllFoodPostsTask extends AsyncTask<Void, Void, List<FoodPost>> {

        private FoodPostDao foodPostDao;
        private GetAllFoodPostsTask(FoodPostDao foodPostDao){
            this.foodPostDao = foodPostDao;
        }

        @Override
        protected List<FoodPost> doInBackground(Void... url){
            return foodPostDao.getAllFoodPosts();
        }
    }
}
