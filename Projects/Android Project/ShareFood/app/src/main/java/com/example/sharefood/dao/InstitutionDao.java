package com.example.sharefood.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.sharefood.entity.FoodStore;
import com.example.sharefood.entity.Institution;

import java.util.List;

@Dao
public interface InstitutionDao {

    @Insert
    void insert(Institution institution);

    @Update
    void update(Institution institution);

    @Delete
    void delete(Institution institution);

    @Query("DELETE FROM institution_table WHERE cnpj = :cnpj")
    void deleteWithCnpj(String cnpj);

    @Query("SELECT * FROM institution_table ORDER BY id DESC")
    List<Institution> getAllInstitutions();
}
