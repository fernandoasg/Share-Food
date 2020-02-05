//package com.example.sharefood.adapter;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.sharefood.R;
//import com.example.sharefood.entity.FoodStore;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class FoodStoreAdapter extends RecyclerView.Adapter<MessageAdapter.FoodStoreHolder> {
//
//    private List<FoodStore> foodStores = new ArrayList<>();
//    private OnItemClickListener listener;
//
//    @NonNull
//    @Override
//    public FoodStoreHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.card_item_store, parent, false);
//
//        return new FoodStoreHolder(itemView);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull FoodStoreHolder holder, int position) {
//        FoodStore currentFoodStore = foodStores.get(position);
//        holder.textViewTitle.setText(currentFoodStore.getTitulo());
//        holder.textViewDescription.setText(currentFoodStore.getDescricao());
//        holder.textViewDistance.setText("100m");
//        holder.textViewRating.setText(currentFoodStore.getAvaliacao() + "");
//    }
//
//    @Override
//    public int getItemCount() {
//        return foodStores.size();
//    }
//
//    public void setFoodStores(List<FoodStore> foodStores){
//        this.foodStores = foodStores;
//        notifyDataSetChanged();
//    }
//
//    class FoodStoreHolder extends RecyclerView.ViewHolder{
//        private TextView textViewTitle;
//        private TextView textViewDescription;
//        private TextView textViewDistance;
//        private TextView textViewRating;
//        private ImageView postImage;
//
//        public FoodStoreHolder(@NonNull View view) {
//            super(view);
//            textViewTitle = view.findViewById(R.id.food_store_product_title);
//            textViewDescription = view.findViewById(R.id.food_store_product_description);
//            textViewDistance = view.findViewById(R.id.food_store_product_price);
//            textViewRating = view.findViewById(R.id.food_store_product_price);
//            postImage = view.findViewById(R.id.food_store_product_image);
//
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    int position = getAdapterPosition();
//                    if(listener != null && position != RecyclerView.NO_POSITION){
//                        listener.onItemClick(foodStores.get(position));
//                    }
//                }
//            });
//        }
//    }
//
//    public interface OnItemClickListener{
//        void onItemClick(FoodStore foodStore);
//    }
//
//    public void filterList(List<FoodStore> filteredFoodStores) {
//        this.foodStores = filteredFoodStores;
//        notifyDataSetChanged();
//    }
//
//    public void setOnItemClickListener(OnItemClickListener listener){
//        this.listener = listener;
//    }
//}
