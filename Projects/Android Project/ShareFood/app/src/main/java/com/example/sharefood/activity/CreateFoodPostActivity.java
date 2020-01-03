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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateFoodPostActivity extends AppCompatActivity {

    private EditText foodNameText;
    private EditText foodDescriptionText;
    private EditText foodDateText;
    private EditText foodHoraParaRetirarText;
    private CreateFoodPostViewModel createFoodPostViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_food_post);

        createFoodPostViewModel = ViewModelProviders.of(this).get(CreateFoodPostViewModel.class);

        foodNameText = findViewById(R.id.add_food_post_name_edit_text);
        foodDescriptionText = findViewById(R.id.add_food_post_description_edit_text);
        foodDateText = findViewById(R.id.add_food_post_date_edit_text);
        foodHoraParaRetirarText = findViewById(R.id.app_food_post_hora_para_retirar);

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
        String foodHoraParaRetirar = foodHoraParaRetirarText.getText().toString();

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

        if(foodHoraParaRetirar.trim().isEmpty()){
            foodHoraParaRetirarText.setError("Você precisa infromar a hora para retirar.");
            return;
        }

        String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

        FoodPost foodPost = new FoodPost(foodName, foodDescription, foodDate, currentDate, foodHoraParaRetirar, 1, 2, 0, 0);

        createFoodPostViewModel.insert(foodPost);

        Toast.makeText(this, "Alimento publicado com sucesso!", Toast.LENGTH_SHORT).show();

        onBackPressed();
    }
}
