package com.example.sharefood.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharefood.R;
import com.example.sharefood.entity.Options;

import java.util.ArrayList;

public class OptionsAdapter extends RecyclerView.Adapter<OptionsAdapter.OptionHolder> {

    private ArrayList<Options> opcoesDoUsario;

    public OptionsAdapter(ArrayList<Options> opcoes){
        this.opcoesDoUsario = opcoes;
    }

    @NonNull
    @Override
    public OptionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.option_card_item, parent, false);

        return new OptionHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OptionHolder holder, int position) {
        Options option = opcoesDoUsario.get(position);
        holder.mainText.setText(option.getMainText());
        if(option.getSecondaryText() != null)
            holder.secondaryText.setText(option.getSecondaryText());
        holder.setImage(option.getImage());
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
            mainText = view.findViewById(R.id.optionNameText);
            secondaryText = view.findViewById(R.id.secondaryText);
            image = view.findViewById(R.id.food_post_image);
            imageButton = view.findViewById(R.id.optionActionButton);
        }

        void setImage(String nome){
            if(nome.equals("perfil"))
                image.setImageResource(R.drawable.ic_person_black_24dp);
            else if(nome.equals("configuracao"))
                image.setImageResource(R.drawable.ic_config_64dp);
        }

        void setImageButton(final String opt){
            final String opcaoParaAbrir = opt;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   System.out.println("Devo abrir  "+ opcaoParaAbrir);

                }
            });
        }
    }
}
