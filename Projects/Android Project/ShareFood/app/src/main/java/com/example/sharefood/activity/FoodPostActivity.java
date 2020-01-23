package com.example.sharefood.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sharefood.Constants;
import com.example.sharefood.R;

public class FoodPostActivity extends AppCompatActivity {

    TextView foodPostTitleText;
    TextView foodPostTimeText;
    TextView foodPostDateText;
    TextView foodPostDescriptionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_post);

        foodPostTitleText = findViewById(R.id.food_post_title);
        foodPostTimeText = findViewById(R.id.food_post_time);
        foodPostDateText = findViewById(R.id.food_post_date);
        foodPostDescriptionText = findViewById(R.id.food_post_description);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();

        if(intent.hasExtra(Constants.EXTRA_FOOD_POST_NAME)){
            String foodPostTitle = intent.getStringExtra(Constants.EXTRA_FOOD_POST_NAME);
            foodPostTitleText.setText(foodPostTitle);
            setTitle(foodPostTitle);

            String foodPostTime = intent.getStringExtra(Constants.EXTRA_FOOD_POST_TIME);
            foodPostTimeText.setText(foodPostTime);

            String foodPostDate = intent.getStringExtra(Constants.EXTRA_FOOD_POST_DATE);
            foodPostDateText.setText(foodPostDate);

            String foodPostDescription = intent.getStringExtra(Constants.EXTRA_FOOD_POST_DESCRIPTION);
            foodPostDescriptionText.setText(foodPostDescription);
        }
    }
}
