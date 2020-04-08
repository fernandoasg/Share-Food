package com.example.sharefood.viewmodel;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.sharefood.entity.FoodProduct;
import com.example.sharefood.entity.Institution;
import com.example.sharefood.repository.FoodProductRepository;
import com.example.sharefood.repository.InstitutionRepository;

import java.util.List;

public class InstitutionViewModel extends AndroidViewModel {

    private InstitutionRepository repository;
    private List<Institution> allInstitutions;

    public InstitutionViewModel(@NonNull Application application) {
        super(application);
        repository = new InstitutionRepository(application);
        allInstitutions = repository.getAllInstitutions();
    }

    public void insert(Institution institution){
        repository.insert(institution);
    }

    public void update(Institution institution){
        repository.update(institution);
    }

    public void delete(Institution institution){
        repository.delete(institution);
    }

    public List<Institution> getAllInstitutions(){
        return allInstitutions;
    }
}
