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
    @ColumnInfo(name = "data_criacao")
    private String dataCriacao;

    @ColumnInfo(name = "itens_aceitar")
    private String itensAceitar;

    @ColumnInfo(name = "usuario_fk")
    private int usuarioFk;

    private String imageUrl;

    public Institution(){

    }

    public Institution(String _nome, String _representante, String _cnpj, String _nascimento, String _missao, int _usuarioFk, String _imageUrl){
        nome = _nome;
        representante = _representante;
        cnpj = _cnpj;
        dataCriacao = _nascimento;
        missao = _missao;
        usuarioFk = _usuarioFk;
        imageUrl = _imageUrl;
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

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setRepresentante(String representante) {
        this.representante = representante;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public void setPodeBuscar(boolean podeBuscar) {
        this.podeBuscar = podeBuscar;
    }

    public void setMensagemInicial(String mensagemInicial) {
        this.mensagemInicial = mensagemInicial;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public void setUsuarioFk(int usuarioFk) {
        this.usuarioFk = usuarioFk;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

