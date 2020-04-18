package com.example.sharefood.repository;

import android.app.Application;
import android.os.AsyncTask;

import com.example.sharefood.ShareFoodDatabase;
import com.example.sharefood.dao.InstitutionDao;
import com.example.sharefood.entity.Institution;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class InstitutionRepository {

    private InstitutionDao institutionDao;
    private List<Institution> allInstitutions;

    public InstitutionRepository(Application application){
        ShareFoodDatabase database = ShareFoodDatabase.getInstance(application);
        institutionDao = database.institutionDao();
    }

    public void insert(Institution institution){

        new InsertInstitutionAsyncTask(institutionDao).execute(institution);
    }

    public void update(Institution institution){
        new UpdateInstitutionAsyncTask(institutionDao).execute(institution);
    }

    public void delete(Institution institution){
        new DeleteInstitutionAsyncTask(institutionDao).execute(institution);
    }

    public void deleteWithCnpj(String cnpj){
        new DeleteInstitutionWithCnpjAsyncTask(institutionDao).execute(cnpj);
    }

    public List<Institution> getAllInstitutions(){
        try {
            return new GetAllInstitutionsTask(institutionDao).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class InsertInstitutionAsyncTask extends AsyncTask<Institution, Void, Void>{

        private InstitutionDao institutionDao;
        private InsertInstitutionAsyncTask(InstitutionDao institutionDao){
            this.institutionDao = institutionDao;
        }

        @Override
        protected Void doInBackground(Institution... institutions) {
            institutionDao.insert(institutions[0]);
            return null;
        }
    }

    private static class UpdateInstitutionAsyncTask extends AsyncTask<Institution, Void, Void>{

        private InstitutionDao institutionDao;
        private UpdateInstitutionAsyncTask(InstitutionDao institutionDao){
            this.institutionDao = institutionDao;
        }

        @Override
        protected Void doInBackground(Institution... institutions) {
            institutionDao.update(institutions[0]);
            return null;
        }
    }

    private static class DeleteInstitutionAsyncTask extends AsyncTask<Institution, Void, Void>{

        private InstitutionDao institutionDao;
        private DeleteInstitutionAsyncTask(InstitutionDao institutionDao){
            this.institutionDao = institutionDao;
        }

        @Override
        protected Void doInBackground(Institution... institutions) {
            institutionDao.delete(institutions[0]);
            return null;
        }
    }

    private static class DeleteInstitutionWithCnpjAsyncTask extends AsyncTask<String, Void, Void>{

        private InstitutionDao institutionDao;
        private DeleteInstitutionWithCnpjAsyncTask(InstitutionDao institutionDao){
            this.institutionDao = institutionDao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            institutionDao.deleteWithCnpj(strings[0]);
            return null;
        }
    }

    private class GetAllInstitutionsTask extends AsyncTask<Void, Void, List<Institution>> {

        private InstitutionDao institutionDao;
        private GetAllInstitutionsTask(InstitutionDao institutionDao){
            this.institutionDao = institutionDao;
        }

        @Override
        protected List<Institution> doInBackground(Void... url){
            return institutionDao.getAllInstitutions();
        }
    }
}
