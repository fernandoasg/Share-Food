package com.example.sharefood.viewmodel;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.sharefood.entity.Institution;
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
        System.out.println("OLHA EU AAQUI NA InstitutionViewModel");
        repository.insert(institution);
        allInstitutions = repository.getAllInstitutions();
    }

    public void update(Institution institution){
        repository.update(institution);
    }

    public void delete(Institution institution){
        repository.delete(institution);
    }

    public void deleteAll(){
        for(Institution i : allInstitutions)
            delete(i);
    }

    public void deleteWithCnpj(String cnpj){
        repository.deleteWithCnpj(cnpj);
    }

    public List<Institution> getAllInstitutions(){
        return allInstitutions;
    }

    public int getAllInstitutionsAmount(){
        return allInstitutions.size();
    }
}
