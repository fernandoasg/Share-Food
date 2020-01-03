package com.example.sharefood.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharefood.R;
import com.example.sharefood.entity.FoodPost;

import java.util.ArrayList;
import java.util.List;

public class FoodPostAdapter extends RecyclerView.Adapter<FoodPostAdapter.FoodPostHolder> {

    private List<FoodPost> foodPosts = new ArrayList<>();

    @NonNull
    @Override
    public FoodPostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.alimento_card_item, parent, false);

        return new FoodPostHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodPostHolder holder, int position) {
        FoodPost currentFoodPost = foodPosts.get(position);
        holder.textViewTitle.setText(currentFoodPost.getTitulo());
        holder.textViewDescription.setText(currentFoodPost.getDescricao());
        holder.textViewDistance.setText("100m");
        holder.textViewDate.setText(currentFoodPost.getDataAberto());
    }

    @Override
    public int getItemCount() {
        return foodPosts.size();
    }

    public void setFoodPosts(List<FoodPost> foodPosts){
        this.foodPosts = foodPosts;
        notifyDataSetChanged();
    }

    class FoodPostHolder extends RecyclerView.ViewHolder{
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewDistance;
        private TextView textViewDate;
        private ImageView postImage;

        public FoodPostHolder(@NonNull View view) {
            super(view);
            textViewTitle = view.findViewById(R.id.food_post_title);
            textViewDescription = view.findViewById(R.id.food_post_description);
            textViewDistance = view.findViewById(R.id.food_post_distance);
            textViewDate = view.findViewById(R.id.food_post_date);
            postImage = view.findViewById(R.id.food_post_image);
        }
    }
}
