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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_post);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();

        if(intent.hasExtra(Constants.EXTRA_FOOD_POST_NAME)){
            TextView text = findViewById(R.id.food_post_title);
            String foodPostTitle = intent.getStringExtra(Constants.EXTRA_FOOD_POST_NAME);
            text.setText(foodPostTitle);
            setTitle(foodPostTitle);
        }
    }
}
