package com.example.sharefood.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "food_post_table")
public class FoodPost {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String titulo;
    private String descricao;

    @ColumnInfo(name = "data_vencimento")
    private String dataVencimento;

    @ColumnInfo(name = "data_aberto")
    private String dataAberto;

    @ColumnInfo(name = "data_fechado")
    private String dataFechado;

    private boolean ativo;

    @ColumnInfo(name = "usuario_origem_fk")
    private int usuarioOrigemFk;

    @ColumnInfo(name = "usuario_destino_fk")
    private int usuarioDestinoFk;

    private int midia;

    public FoodPost(String titulo, String descricao, String dataVencimento, int usuarioOrigemFk, int midia) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataVencimento = dataVencimento;
        this.usuarioOrigemFk = usuarioOrigemFk;
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

    public String getDataVencimento() {
        return dataVencimento;
    }

    public String getDataAberto() {
        return dataAberto;
    }

    public String getDataFechado() {
        return dataFechado;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public int getUsuarioOrigemFk() {
        return usuarioOrigemFk;
    }

    public int getUsuarioDestinoFk() {
        return usuarioDestinoFk;
    }

    public int getMidia() {
        return midia;
    }

    public void setDataAberto(String dataAberto) {
        this.dataAberto = dataAberto;
    }

    public void setDataFechado(String dataFechado) {
        this.dataFechado = dataFechado;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public void setUsuarioDestinoFk(int usuarioDestinoFk) {
        this.usuarioDestinoFk = usuarioDestinoFk;
    }
}
