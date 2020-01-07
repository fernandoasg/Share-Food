package com.example.sharefood.entity;

public class User {

    private int id;
    private String nome;
    private String email;
    private String celular;
    private String senha;
    private int midiaFk;

    public User(String nome, String email, String celular, String senha) {
        this.nome = nome;
        this.email = email;
        this.celular = celular;
        this.senha = senha;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMidiaFk(int midiaFk) {
        this.midiaFk = midiaFk;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getCelular() {
        return celular;
    }

    public String getSenha() {
        return senha;
    }

    public int getMidiaFk() {
        return midiaFk;
    }
}
