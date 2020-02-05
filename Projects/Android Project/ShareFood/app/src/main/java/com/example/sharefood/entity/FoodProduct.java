package com.example.sharefood.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "food_product_table")
public class FoodProduct {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String titulo;

    private String descricao;

    private double preco;

    private boolean ativo;

    @ColumnInfo(name = "store_origem_fk")
    private int storeOrigemFk;

    private int midia;

    public FoodProduct(String titulo, String descricao, double preco, int storeOrigemFk, int midia) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.preco = preco;
        this.storeOrigemFk = storeOrigemFk;
        this.midia = midia;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public int getMidia() {
        return midia;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public int getStoreOrigemFk() {
        return storeOrigemFk;
    }
}
