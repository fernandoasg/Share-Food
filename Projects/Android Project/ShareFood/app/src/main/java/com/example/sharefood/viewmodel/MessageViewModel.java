package com.example.sharefood.viewmodel;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.sharefood.entity.FoodProduct;
import com.example.sharefood.entity.Message;
import com.example.sharefood.repository.FoodProductRepository;
import com.example.sharefood.repository.MessageRepository;

import java.util.ArrayList;
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

    public List<Message> getAllLastMessages(){
        int allMessagesCount = allMessages.size();
        List<Message> lastMessagesList = new ArrayList<>();
        for(int i = 0; i < allMessagesCount; i++){
            Message currentMessage = allMessages.get(i);
            /*for(int j = i + 1; j < allMessagesCount; j++){
                if(allMessages.get(j).getParentMessageFk() == currentMessage.getParentMessageFk()){
                    currentMessage = allMessages.get(i);
                }
            }
            lastMessagesList.add()*/
            if(i == 0 || i == 2 || i == 5){
                lastMessagesList.add(allMessages.get(i));
            }
            System.out.println(currentMessage.getMessageBody() + "  " + currentMessage.getId());
        }

        return  lastMessagesList;
    }

    public List<Message> getMessagesByLastMessage(int lastPos){
        System.out.println(lastPos);
        Message currentMessage = null;
        for(int i =0; i < allMessages.size(); i++){
            if(allMessages.get(i).getId() == lastPos){
                currentMessage = allMessages.get(i);
                break;
            }
        }
        List<Message> messages = new ArrayList<>();
        System.out.println(currentMessage.getMessageBody());
        System.out.println(currentMessage.getParentMessageFk());
        while(currentMessage.getParentMessageFk() != -1){
            messages.add(currentMessage);
            currentMessage = allMessages.get(currentMessage.getParentMessageFk());
        }
        messages.add(currentMessage);

        return messages;
    }
}
