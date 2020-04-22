package com.example.sharefood.adapter;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharefood.R;
import com.example.sharefood.entity.FoodPost;
import com.example.sharefood.util.ImageUtil;

import java.util.ArrayList;
import java.util.List;

public class FoodPostAdapter extends RecyclerView.Adapter<FoodPostAdapter.FoodPostHolder> {

    private List<FoodPost> foodPosts = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public FoodPostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item_food, parent, false);

        return new FoodPostHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodPostHolder holder, int position) {
        FoodPost currentFoodPost = foodPosts.get(position);
        holder.textViewTitle.setText(currentFoodPost.getTitulo());
        holder.textViewDescription.setText(currentFoodPost.getDescricao());
        holder.textViewDistance.setText("100m");
        holder.textViewDate.setText(currentFoodPost.getDataAberto());
        holder.setImage(currentFoodPost.getImageUrl(), currentFoodPost.getDonator() + currentFoodPost.getDataAberto().replace('/',' ').replaceAll("\\s+",""));
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
            textViewTitle = view.findViewById(R.id.institute_title);
            textViewDescription = view.findViewById(R.id.institute_description);
            textViewDistance = view.findViewById(R.id.institute_distance);
            textViewDate = view.findViewById(R.id.food_institute_date);
            postImage = view.findViewById(R.id.institute_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(foodPosts.get(position));
                    }
                }
            });
        }

        void setImage(String imagePath, String name){
            if(imagePath != null){
                Bitmap profileBitmap = ImageUtil.loadImageFromStorage(imagePath, name);
                postImage.setImageBitmap(profileBitmap);
            }else
                postImage.setImageResource(R.drawable.ic_attach_money_black_24dp);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(FoodPost foodPost);
    }

    public void filterList(List<FoodPost> filterdFoodPosts) {
        this.foodPosts = filterdFoodPosts;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
