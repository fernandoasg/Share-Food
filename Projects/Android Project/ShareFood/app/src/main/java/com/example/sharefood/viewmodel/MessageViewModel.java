package com.example.sharefood.viewmodel;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.sharefood.entity.FoodProduct;
import com.example.sharefood.entity.Message;
import com.example.sharefood.repository.FoodProductRepository;
import com.example.sharefood.repository.MessageRepository;

import java.util.List;

public class MessageViewModel extends AndroidViewModel {

    private MessageRepository repository;
    private List<Message> allMessages;

    public MessageViewModel(@NonNull Application application) {
        super(application);
        repository = new MessageRepository(application);
        allMessages = repository.getAllMessages();
    }

    public void insert(Message message){
        repository.insert(message);
    }

    public List<Message> getAllMessages(){
        return allMessages;
    }
}
