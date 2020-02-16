package com.example.sharefood.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sharefood.Constants;
import com.example.sharefood.R;
import com.example.sharefood.adapter.FoodProductAdapter;
import com.example.sharefood.entity.FoodProduct;
import com.example.sharefood.viewmodel.FoodProductViewModel;

import java.util.ArrayList;
import java.util.List;


public class FoodStoreActivity extends AppCompatActivity {

    TextView foodStoreTitleText;
    TextView foodStoreDescriptionText;
    TextView foodStoreRatingText;
    TextView foodStoreOwnerText;

    ImageView foodStoreImage;

    FoodProductViewModel foodProductViewModel;
    RecyclerView productsRecyclerView;
    List<FoodProduct> foodProductList;
    FoodProductAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_store);

        foodStoreTitleText = findViewById(R.id.food_institute_title);
        foodStoreDescriptionText = findViewById(R.id.food_institute_description);
        foodStoreRatingText = findViewById(R.id.food_institute_distance);
        foodStoreOwnerText = findViewById(R.id.food_store_owner);
        foodStoreImage = findViewById(R.id.food_institute_image);
        productsRecyclerView = findViewById(R.id.store_products_recycler_view);
        productsRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        productsRecyclerView.setHasFixedSize(true);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();

        if(intent.hasExtra(Constants.EXTRA_FOOD_STORE_ID)){
            String foodStoreTitle = intent.getStringExtra(Constants.EXTRA_FOOD_STORE_TITLE);
            foodStoreTitleText.setText(foodStoreTitle);
            setTitle(foodStoreTitle);

            String foodStoreDescription = intent.getStringExtra(Constants.EXTRA_FOOD_STORE_DESCRIPTION);
            foodStoreDescriptionText.setText(foodStoreDescription);

            String foodStoreRate = intent.getStringExtra(Constants.EXTRA_FOOD_STORE_RATE);
            foodStoreRatingText.setText(foodStoreRate);
        }

        foodProductViewModel = ViewModelProviders.of(this).get(FoodProductViewModel.class);
        recyclerAdapter = new FoodProductAdapter();

        foodProductList = new ArrayList<>();
        foodProductList = foodProductViewModel.getAllFoodProducts();

        recyclerAdapter.setFoodProducts(foodProductList);
        recyclerAdapter.setOnItemClickListener(new FoodProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(FoodProduct foodProduct) {
                Intent intent = new Intent(getApplicationContext(), FoodProductActivity.class);
                startActivity(intent);
            }
        });

        productsRecyclerView.setAdapter(recyclerAdapter);

    }
}
