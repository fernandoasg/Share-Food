package com.example.sharefood.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharefood.R;
import com.example.sharefood.entity.FoodProduct;

import java.util.ArrayList;
import java.util.List;

public class FoodProductAdapter extends RecyclerView.Adapter<FoodProductAdapter.FoodProductHolder> {

    private List<FoodProduct> foodProducts = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public FoodProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item_store_product, parent, false);

        return new FoodProductHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodProductHolder holder, int position) {
        FoodProduct currentFoodProduct = foodProducts.get(position);
        holder.textViewTitle.setText(currentFoodProduct.getTitulo());
        holder.textViewDescription.setText(currentFoodProduct.getDescricao());
        holder.textViewPrice.setText("R$ " + currentFoodProduct.getPreco());
    }

    @Override
    public int getItemCount() {
        return foodProducts.size();
    }

    public void setFoodProducts(List<FoodProduct> foodProducts){
        this.foodProducts = foodProducts;
        notifyDataSetChanged();
    }

    class FoodProductHolder extends RecyclerView.ViewHolder{
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewPrice;
        private ImageView productImage;

        public FoodProductHolder(@NonNull View view) {
            super(view);
            textViewTitle = view.findViewById(R.id.food_institute_title);
            textViewDescription = view.findViewById(R.id.food_institute_description);
            textViewPrice = view.findViewById(R.id.food_institute_distance);
            productImage = view.findViewById(R.id.food_institute_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(foodProducts.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(FoodProduct foodProduct);
    }

    public void filterList(List<FoodProduct> filteredFoodProducts) {
        this.foodProducts = filteredFoodProducts;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
