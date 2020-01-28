package com.example.sharefood.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sharefood.R;
import com.example.sharefood.SessionManager;
import com.example.sharefood.entity.FoodPost;
import com.example.sharefood.viewmodel.CreateFoodPostViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateFoodPostActivity extends AppCompatActivity {

    private EditText foodNameText;
    private EditText foodDescriptionText;
    private EditText foodDateText;
    private EditText foodHoraParaRetirarText;
    private ImageView foodLocalizationImage;
    private CreateFoodPostViewModel createFoodPostViewModel;

    private FusedLocationProviderClient client;
    private Boolean mLocationPermissionGranted = false;
    private double latitude = 0;
    private double longitude = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_food_post);

        createFoodPostViewModel = ViewModelProviders.of(this).get(CreateFoodPostViewModel.class);

        foodNameText = findViewById(R.id.add_food_post_name_edit_text);
        foodDescriptionText = findViewById(R.id.add_food_post_description_edit_text);
        foodDateText = findViewById(R.id.add_food_post_date_edit_text);
        foodHoraParaRetirarText = findViewById(R.id.app_food_post_hora_para_retirar);
        foodLocalizationImage = findViewById(R.id.add_food_post_localization_image);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);
        setTitle("Compartilhar Alimento");

        Button createFoodPostButton = findViewById(R.id.add_food_post_button);
        createFoodPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveFoodPost();
            }
        });

        foodLocalizationImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Pegar posição");
                getDeviceLocation();
            }
        });

        getLocationPermission();
    }

    private void getLocationPermission() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            mLocationPermissionGranted = true;
        }else{
            ActivityCompat.requestPermissions(this,
                    permissions, 1234);
        }
    }

    private void getDeviceLocation() {
        client = LocationServices.
                getFusedLocationProviderClient(this);
        try {
            if(mLocationPermissionGranted){
                final Task location = client.
                        getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Location currentLocation = (Location) task.getResult();
                            latitude = currentLocation.getLatitude();
                            longitude = currentLocation.getLongitude();
                            System.out.println("Latitude:"+ latitude);
                            System.out.println("Longitude:"+ longitude);
                        }else{
                            Log.d("TAG","onComplete error found location.");
                        }
                    }
                });
            }
        }catch (SecurityException e){
            Log.d("TAG","onComplete error location is null.");
        }
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

        FoodPost foodPost = new FoodPost(foodName, foodDescription, foodDate, currentDate, foodHoraParaRetirar, longitude, latitude, 0, 0);

        createFoodPostViewModel.insert(foodPost);

        Toast.makeText(this, "Alimento publicado com sucesso!", Toast.LENGTH_SHORT).show();

        onBackPressed();
    }
}
