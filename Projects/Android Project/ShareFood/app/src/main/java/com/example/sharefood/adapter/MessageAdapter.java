package com.example.sharefood.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharefood.R;
import com.example.sharefood.entity.FoodStore;
import com.example.sharefood.entity.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageHolder> {

    private List<Message> messages = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item_message, parent, false);

        return new MessageHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageHolder holder, int position) {
        Message currentMessage = messages.get(position);
        holder.textViewUserName.setText("Nome do usuário " + currentMessage.getUsuarioFk());
        holder.textViewMessageContent.setText(currentMessage.getMessageBody());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void setMessages(List<Message> messages){
        this.messages = messages;
        notifyDataSetChanged();
    }

    class MessageHolder extends RecyclerView.ViewHolder{
        private TextView textViewUserName;
        private TextView textViewMessageContent;
        private ImageView userImage;

        public MessageHolder(@NonNull View view) {
            super(view);
            textViewUserName = view.findViewById(R.id.user_profile_name);
            textViewMessageContent = view.findViewById(R.id.user_message_last_content);
            userImage = view.findViewById(R.id.user_profile_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(messages.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(Message message);
    }

    public void filterList(List<Message> filteredMessages) {
        this.messages = filteredMessages;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
