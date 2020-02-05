package com.example.sharefood.repository;

import android.app.Application;
import android.os.AsyncTask;

import com.example.sharefood.ShareFoodDatabase;
import com.example.sharefood.dao.MessageDao;
import com.example.sharefood.entity.FoodPost;
import com.example.sharefood.entity.Message;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MessageRepository {

    private MessageDao messageDao;
    private List<Message> messages;

    public MessageRepository(Application application){
        ShareFoodDatabase database = ShareFoodDatabase.getInstance(application);
        messageDao = database.messageDao();
    }

    public void insert(Message message){
        new InsertMessageAsyncTask(messageDao).execute(message);
    }

    public List<Message> getAllMessages(){
        try {
            return new GetAllMessagesTask(messageDao).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class InsertMessageAsyncTask extends AsyncTask<Message, Void, Void>{

        private MessageDao messageDao;
        private InsertMessageAsyncTask(MessageDao messageDao){
            this.messageDao = messageDao;
        }

        @Override
        protected Void doInBackground(Message... messages) {
            messageDao.insert(messages[0]);
            return null;
        }
    }

    private class GetAllMessagesTask extends AsyncTask<Void, Void, List<Message>> {

        private MessageDao messageDao;
        private GetAllMessagesTask(MessageDao messageDao){
            this.messageDao = messageDao;
        }

        @Override
        protected List<Message> doInBackground(Void... url){
            return messageDao.getAllMessages();
        }
    }
}
