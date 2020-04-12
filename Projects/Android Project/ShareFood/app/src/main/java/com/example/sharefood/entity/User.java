package com.example.sharefood.entity;

public class User {

    private int id;
    public String uId;
    private String nome;
    private String email;
    private String userType;
    private int midiaFk;

    public User(String uId, String nome, String email, String userType) {
        this.uId = uId;
        this.nome = nome;
        this.email = email;
        this.userType = userType;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
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

    public String getUserType() {
        return userType;
    }

    public int getMidiaFk() {
        return midiaFk;
    }
}
