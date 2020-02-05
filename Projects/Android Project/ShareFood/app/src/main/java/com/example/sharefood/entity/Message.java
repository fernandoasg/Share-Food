package com.example.sharefood.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "message_table")
public class Message {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "message_body")
    private String messageBody;

    @ColumnInfo(name = "data_criada")
    private String dataCriada;

    @ColumnInfo(name = "usuario_fk")
    private int usuarioFk;

    @ColumnInfo(name = "parent_message_fk")
    private int parentMessageFk;

    @ColumnInfo(name = "message_status")
    private boolean messageStatus;

    public Message(String messageBody, String dataCriada, int usuarioFk, int parentMessageFk) {
        this.messageBody = messageBody;
        this.dataCriada = dataCriada;
        this.usuarioFk = usuarioFk;
        this.parentMessageFk = parentMessageFk;
        this.messageStatus = true;
    }

    public int getId() {
        return id;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public String getDataCriada() {
        return dataCriada;
    }

    public int getUsuarioFk() {
        return usuarioFk;
    }

    public int getParentMessageFk() {
        return parentMessageFk;
    }

    public boolean isMessageStatus() {
        return messageStatus;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMessageStatus(boolean messageStatus) {
        this.messageStatus = messageStatus;
    }
}
