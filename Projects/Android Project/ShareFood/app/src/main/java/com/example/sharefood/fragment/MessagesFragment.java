package com.example.sharefood.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharefood.Constants;
import com.example.sharefood.R;
import com.example.sharefood.activity.MessageActivity;
import com.example.sharefood.adapter.MessageAdapter;
import com.example.sharefood.entity.Message;
import com.example.sharefood.viewmodel.MessageViewModel;

import java.util.ArrayList;
import java.util.List;


public class MessagesFragment extends Fragment {

    RecyclerView messagesRecyclerView;
    TextView noMessagesTextView;

    MessageViewModel messageViewModel;
    List<Message> messageList;
    MessageAdapter messageAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mensagens, container, false);

        getActivity().setTitle("Mensagens");
        noMessagesTextView = view.findViewById(R.id.messages_recycler_empty_text);

        messagesRecyclerView = view.findViewById(R.id.messages_recycler_view);
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        messagesRecyclerView.setHasFixedSize(true);

        messageViewModel = ViewModelProviders.of(getActivity()).get(MessageViewModel.class);

        messageAdapter = new MessageAdapter();

        messageList = new ArrayList<>();
        messageList = messageViewModel.getAllLastMessages();

        if(messageList.size() > 0){
            messagesRecyclerView.setVisibility(View.VISIBLE);
            noMessagesTextView.setVisibility(View.GONE);
            messageAdapter.setMessages(messageList);
            messageAdapter.setOnItemClickListener(new MessageAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Message message) {
                    Intent intent = new Intent(getActivity(), MessageActivity.class);
                    intent.putExtra(Constants.EXTRA_MESSAGE_ID, message.getId());
                    System.out.println(message.getMessageBody());
                    startActivity(intent);
                }
            });

            messagesRecyclerView.setAdapter(messageAdapter);
        }else{
            noMessagesTextView.setVisibility(View.VISIBLE);
            messagesRecyclerView.setVisibility(View.GONE);
        }

        return view;
    }
}
