package com.example.sharefood.entity;

public class User {

    private int id;
    private String nome;
    private String email;
    private String senha;
    private String userType;
    private int midiaFk;

    public User(String nome, String email, String senha, String userType) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.userType = userType;
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

    public String getSenha() {
        return senha;
    }

    public String getUserType() {
        return userType;
    }

    public int getMidiaFk() {
        return midiaFk;
    }
}
