package com.example.sharefood.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "food_store_table")
public class FoodStore {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String titulo;
    private String descricao;

    @ColumnInfo(name = "horario_de_funcionamento")
    private String horarioDeFuncionamento;

    private double longitude;

    private double latitude;

    @ColumnInfo(name = "data_aberto")
    private String dataAberto;

    private boolean ativo;

    private float avaliacao;

    @ColumnInfo(name = "usuario_dono_fk")
    private int usuarioDonoFk;

    private int midia;

    public FoodStore(String titulo, String descricao, String dataAberto, String horarioDeFuncionamento, double longitude, double latitude, int usuarioDonoFk, int midia) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataAberto = dataAberto;
        this.horarioDeFuncionamento = horarioDeFuncionamento;
        this.longitude = longitude;
        this.latitude = latitude;
        this.usuarioDonoFk = usuarioDonoFk;
        this.midia = midia;
    }

    public float getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(float avaliacao) {
        this.avaliacao = avaliacao;
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

    public String getHorarioDeFuncionamento() {
        return horarioDeFuncionamento;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getDataAberto() {
        return dataAberto;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public int getUsuarioDonoFk() {
        return usuarioDonoFk;
    }

    public int getMidia() {
        return midia;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
