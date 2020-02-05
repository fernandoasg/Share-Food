package com.example.sharefood.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.sharefood.Constants;
import com.example.sharefood.R;
import com.example.sharefood.adapter.MessageAdapter;
import com.example.sharefood.entity.Message;
import com.example.sharefood.viewmodel.MessageViewModel;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity {

    RecyclerView messagesRecyclerView;

    MessageViewModel messageViewModel;
    List<Message> messageList;
    MessageAdapter messageAdapter;

    private int lastMessageId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();

        messagesRecyclerView = findViewById(R.id.current_message_recycler_view);
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        messagesRecyclerView.setHasFixedSize(true);

        messageViewModel = ViewModelProviders.of(this).get(MessageViewModel.class);

        messageAdapter = new MessageAdapter();
        messageList = new ArrayList<>();

        if(intent.hasExtra(Constants.EXTRA_MESSAGE_ID)){
            lastMessageId = intent.getIntExtra(Constants.EXTRA_MESSAGE_ID, -1);
            System.out.println(lastMessageId);
            this.setTitle("Nome do Usuário " + lastMessageId);
            messageList = messageViewModel.getMessagesByLastMessage(lastMessageId);
        }

        messageAdapter.setMessages(messageList);

        messagesRecyclerView.setAdapter(messageAdapter);
    }
}
