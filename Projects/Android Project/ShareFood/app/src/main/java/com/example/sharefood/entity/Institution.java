package com.example.sharefood.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName =  "institution_table")
public class Institution {

    @PrimaryKey(autoGenerate =  true)
    private int id;
    private String nome;
    private String representante;
    private String cnpj;

    @ColumnInfo(name = "pode_buscar")
    private boolean podeBuscar;

    @ColumnInfo(name = "mensagem_inicial")
    private String mensagemInicial;
    private String missao;

    @ColumnInfo(name = "itens_aceitar")
    private String itensAceitar;

    @ColumnInfo(name = "usuario_fk")
    private int usuarioFk;

    public Institution(String _nome, String _representante, String _cnpj, boolean _podeBuscar, String _mensagemInicial, int _usuarioFk){
        nome = _nome;
        representante = _representante;
        cnpj = _cnpj;
        podeBuscar = _podeBuscar;
        mensagemInicial = _mensagemInicial;
        usuarioFk = _usuarioFk;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMissao(String missao) {
        this.missao = missao;
    }

    public void setItensAceitar(String itensAceitar) {
        this.itensAceitar = itensAceitar;
    }

    public int getId(){
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getRepresentante() {
        return representante;
    }

    public String getCnpj() {
        return cnpj;
    }

    public boolean isPodeBuscar() {
        return podeBuscar;
    }

    public String getMensagemInicial() {
        return mensagemInicial;
    }

    public String getMissao() {
        return missao;
    }

    public String getItensAceitar() {
        return itensAceitar;
    }

    public int getUsuarioFk() {
        return usuarioFk;
    }
}

