package com.example.sharefood.adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharefood.Constants;
import com.example.sharefood.R;
import com.example.sharefood.SessionManager;
import com.example.sharefood.activity.ConfigurationActivity;
import com.example.sharefood.activity.ProfileActivity;
import com.example.sharefood.activity.SharedFoodActivity;
import com.example.sharefood.entity.Options;
import com.example.sharefood.util.ImageUtil;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;

public class OptionsAdapter extends RecyclerView.Adapter<OptionsAdapter.OptionHolder> {

    private ArrayList<Options> opcoesDoUsario;

    public OptionsAdapter(ArrayList<Options> opcoes){
        this.opcoesDoUsario = opcoes;
    }

    @NonNull
    @Override
    public OptionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_option, parent, false);

        return new OptionHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OptionHolder holder, int position) {
        Options option = opcoesDoUsario.get(position);
        holder.mainText.setText(option.getMainText());
        if(option.getSecondaryText() != null)
            holder.secondaryText.setText(option.getSecondaryText());
        holder.setImage(option.getImage(), option.getWhereToOpen(), option.getSecondaryText());
        holder.setImageButton(option.getWhereToOpen());
    }



    @Override
    public int getItemCount() {
        if(opcoesDoUsario != null){
            return opcoesDoUsario.size();
        }else{
            return 0;
        }
    }

    public static class OptionHolder extends RecyclerView.ViewHolder{
        public final View view;
        public final TextView mainText;
        public final TextView secondaryText;
        public final ImageView image;
        public final ImageButton imageButton;

        public OptionHolder(View view){
            super(view);
            this.view = view;
            mainText = view.findViewById(R.id.user_profile_name);
            secondaryText = view.findViewById(R.id.user_message_last_content);
            image = view.findViewById(R.id.user_profile_image);
            imageButton = view.findViewById(R.id.option_action_button);
        }

        void setImage(String imagePath, String whereToOpen, String email){
            if(whereToOpen.equals("Profile")){
                if(imagePath != null){
                    Bitmap profileBitmap = ImageUtil.loadImageFromStorage(imagePath, email);
                    image.setImageBitmap(profileBitmap);
                }else
                    image.setImageResource(R.drawable.ic_person_black_24dp);
            }
            else if(whereToOpen.equals("SharedFood"))
                image.setImageResource(R.drawable.ic_config_64dp);
        }

        void setImageButton(final String opt){
            final String opcaoParaAbrir = opt;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   System.out.println("Devo abrir  "+ opcaoParaAbrir);
                   switch (opcaoParaAbrir){
                       case "Profile":
                           Intent intent = new Intent(view.getContext(), ProfileActivity.class);
                           view.getContext().startActivity(intent);
                           break;
                       case "SharedFood":
                           Intent intent1 = new Intent(view.getContext(), SharedFoodActivity.class);
                           view.getContext().startActivity(intent1);
                           break;
                       case "Configuration":
                           Intent intent2 = new Intent(view.getContext(), ConfigurationActivity.class);
                           view.getContext().startActivity(intent2);
                           break;

                   }

                }
            });
        }
    }
}
