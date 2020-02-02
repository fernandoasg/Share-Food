package com.example.sharefood.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sharefood.Constants;
import com.example.sharefood.R;

public class FoodStoreActivity extends AppCompatActivity {

    TextView foodStoreTitleText;
    TextView foodStoreDescriptionText;
    TextView foodStoreRatingText;
    TextView foodStoreOwnerText;

    ImageView foodStoreImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_store);

        foodStoreTitleText = findViewById(R.id.food_store_title);
        foodStoreDescriptionText = findViewById(R.id.food_store_description);
        foodStoreRatingText = findViewById(R.id.food_store_rate);
        foodStoreOwnerText = findViewById(R.id.food_store_owner);
        foodStoreImage = findViewById(R.id.food_store_image);

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
    }
}
