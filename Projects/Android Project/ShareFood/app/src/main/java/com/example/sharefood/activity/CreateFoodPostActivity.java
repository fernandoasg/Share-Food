package com.example.sharefood.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sharefood.R;
import com.example.sharefood.entity.FoodPost;
import com.example.sharefood.viewmodel.CreateFoodPostViewModel;

public class CreateFoodPostActivity extends AppCompatActivity {

    private EditText foodNameText;
    private EditText foodDescriptionText;
    private EditText foodDateText;
    private CreateFoodPostViewModel createFoodPostViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_food_post);

        createFoodPostViewModel = ViewModelProviders.of(this).get(CreateFoodPostViewModel.class);

        foodNameText = findViewById(R.id.add_food_post_name_edit_text);
        foodDescriptionText = findViewById(R.id.add_food_post_description_edit_text);
        foodDateText = findViewById(R.id.add_food_post_date_edit_text);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);
        setTitle("Compartilhar Alimento");

        Button createFoodPostButton = findViewById(R.id.add_food_post_button);
        createFoodPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveFoodPost();
            }
        });
    }

    private void saveFoodPost(){
        String foodName = foodNameText.getText().toString();
        String foodDescription = foodDescriptionText.getText().toString();
        String foodDate = foodDateText.getText().toString();

        if(foodName.trim().isEmpty()){
            foodNameText.setError("Você precisa informar o nome do alimento.");
            return;
        }

        if(foodDescription.trim().isEmpty()){
            foodDescriptionText.setError("Você precisa informar a descrição do alimento.");
            return;
        }

        if(foodDate.trim().isEmpty()){
            foodDateText.setError("Você precisa informar a data de vencimento do alimento.");
            return;
        }

        FoodPost foodPost = new FoodPost(foodName, foodDescription, foodDate, 0, 0);

        createFoodPostViewModel.insert(foodPost);

        Toast.makeText(this, "Alimento publicado com sucesso!", Toast.LENGTH_SHORT).show();

        onBackPressed();
    }
}
