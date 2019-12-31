package com.example.sharefood.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "food_post_table")
public class FoodPost {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String titulo;
    private String descricao;

    @ColumnInfo(name = "data_vencimento")
    private Date dataVencimento;

    @ColumnInfo(name = "tempo_para_retirar")
    private float tempoParaRetirar;

    @ColumnInfo(name = "data_aberto")
    private Date dataAberto;

    @ColumnInfo(name = "data_fechado")
    private Date dataFechado;

    private boolean ativo;

    @ColumnInfo(name = "usuario_origem_fk")
    private int usuarioOrigemFk;

    @ColumnInfo(name = "usuario_destino_fk")
    private int usuarioDestinoFk;

    private int midia;

    public FoodPost(String titulo, String descricao, Date dataVencimento, float tempoParaRetirar, int usuarioOrigemFk, int midia) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataVencimento = dataVencimento;
        this.tempoParaRetirar = tempoParaRetirar;
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

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public float getTempoParaRetirar() {
        return tempoParaRetirar;
    }

    public Date getDataAberto() {
        return dataAberto;
    }

    public Date getDataFechado() {
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
}
