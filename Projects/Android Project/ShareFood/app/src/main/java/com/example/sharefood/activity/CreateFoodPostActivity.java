package com.example.sharefood.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.se.omapi.Session;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sharefood.Constants;
import com.example.sharefood.R;
import com.example.sharefood.SessionManager;
import com.example.sharefood.entity.FoodPost;
import com.example.sharefood.util.ImageUtil;
import com.example.sharefood.viewmodel.CreateFoodPostViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CreateFoodPostActivity extends AppCompatActivity {

    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;

    private EditText foodNameText;
    private EditText foodDescriptionText;
    private EditText foodDateText;
    private EditText foodHoraParaRetirarText;
    private ImageView foodLocalizationImage;
    private TextView foodTakePictureText;
    private ImageView foodPictureImage;
    private CreateFoodPostViewModel createFoodPostViewModel;
    private FoodPost foodPost;

    private FusedLocationProviderClient client;
    private Boolean mLocationPermissionGranted = false;
    private double latitude = 0;
    private double longitude = 0;

    // Post Image
    private Uri foodImageUri;
    Uri imgUri = null;
    Bitmap imgBitmap = null;
    String userEmail = null;
    String imageUrl = null;

    FirebaseFirestore firestore;
    private UploadTask uploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_food_post);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);
        setTitle("Compartilhar Alimento");

        createFoodPostViewModel = ViewModelProviders.of(this).get(CreateFoodPostViewModel.class);

        SessionManager sessionManager = new SessionManager(this);
        userEmail = sessionManager.getSavedString(Constants.USER_EMAIL);
        latitude = sessionManager.getLatitude();
        longitude = sessionManager.getLongitude();

        foodNameText = findViewById(R.id.add_food_post_name_edit_text);
        foodDescriptionText = findViewById(R.id.add_food_post_description_edit_text);
        foodDateText = findViewById(R.id.add_food_post_date_edit_text);
        foodHoraParaRetirarText = findViewById(R.id.app_food_post_hora_para_retirar);

        foodLocalizationImage = findViewById(R.id.add_food_post_localization_image);
        if(latitude == 0 && longitude == 0){
            foodLocalizationImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_my_location_red_24dp));
        }else{
            foodLocalizationImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_my_location_green_24dp));
        }

        foodPictureImage = findViewById(R.id.add_food_post_image);
        foodPictureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage(CreateFoodPostActivity.this);
            }
        });

        foodTakePictureText = findViewById(R.id.add_food_post_image_text);
        foodTakePictureText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage(CreateFoodPostActivity.this);
            }
        });


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
                getDeviceLocation();
            }
        });

        getLocationPermission();
    }

    private void saveFoodPost(){
        final String foodName = foodNameText.getText().toString().trim();
        final String foodDescription = foodDescriptionText.getText().toString().trim();
        final String foodDate = foodDateText.getText().toString().trim();
        final String foodHoraParaRetirar = foodHoraParaRetirarText.getText().toString().trim();

        if(foodName.isEmpty()){
            foodNameText.setError("Você precisa informar o nome do alimento.");
            foodNameText.requestFocus();
            return;
        }

        if(foodDescription.isEmpty()){
            foodDescriptionText.setError("Você precisa informar a descrição do alimento.");
            foodDescriptionText.requestFocus();
            return;
        }

        if(foodDate.isEmpty()){
            foodDateText.setError("Você precisa informar a data de vencimento do alimento.");
            foodDateText.requestFocus();
            return;
        }

        if(foodHoraParaRetirar.isEmpty()){
            foodHoraParaRetirarText.setError("Você precisa informar a hora para retirar.");
            foodHoraParaRetirarText.requestFocus();
            return;
        }

        if(imgBitmap == null){
            return;
        }

        if(latitude == 0 && longitude == 0){
            return;
        }

        // Salva a imagem localmente
        String imageName = userEmail + Calendar.getInstance().getTime().toString().replaceAll("\\s+","");
        imageUrl = ImageUtil.saveToInternalStorage(this, imgBitmap, imageName);
        imgUri = Uri.fromFile(new File(imageUrl + "/" + imageName));

        // Pega a data atual
        final String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

        // Salva o FoodPost localmente
        FoodPost foodPost = new FoodPost(foodName, foodDescription, foodDate, currentDate, foodHoraParaRetirar, longitude, latitude, 0, "me", imageUrl);
        createFoodPostViewModel.insert(foodPost);

        // Salva imagem no Storage
        StorageReference reference = FirebaseStorage.getInstance().getReference("Images").child(imgUri.getLastPathSegment());

        uploadTask = (UploadTask) reference.putFile(imgUri)
            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!urlTask.isSuccessful());

                    Uri downloadUrl = urlTask.getResult();

                    SessionManager sessionManager = new SessionManager(CreateFoodPostActivity.this);
                    String userId = sessionManager.getUserId();
                    // Salvar o FoodPost no Firestore

                    firestore = FirebaseFirestore.getInstance();

                    Map<String, Object> data = new HashMap<>();
                    data.put("title", foodName);
                    data.put("description", foodDescription);
                    data.put("timeToTake", foodHoraParaRetirar);
                    data.put("longitude", longitude);
                    data.put("latitude", latitude);
                    data.put("dueDate", foodDate);
                    data.put("publicationDate", currentDate);
                    data.put("active", true);
                    data.put("donator", userId);
                    data.put("imageUrl", downloadUrl.toString());

                    firestore.collection("donations")
                            .add(data)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    System.out.println("Adicionou doação");
                                    Toast.makeText(CreateFoodPostActivity.this, "Alimento publicado com sucesso!",
                                            Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    System.out.println("Erro ao adicionar doação: " + e);
                                }
                            });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    System.out.println("Deu erro aqui");
                }
            });
    }


    private void selectImage(Context context) {
        final CharSequence[] options = { "Tirar foto", "Suas imagens" };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Escolha a foto da doação");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Tirar foto")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals("Suas imagens")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);

                }
            }
        });
        builder.show();
    }

    private void openCamera(){
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Nova Foto");
        values.put(MediaStore.Images.Media.DESCRIPTION, "da câmera");
        foodImageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        // Câmera intent
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, foodImageUri);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);
    }

    // trata resultado da permissão
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE:{
                if(grantResults.length > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    openCamera();
                }else{
                    Toast.makeText(this, "Permissão para usar a Câmera negada.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        imgBitmap = selectedImage;
                        foodPictureImage.setImageBitmap(imgBitmap);
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage =  data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            try{
                                imgBitmap = ImageUtil.decodeUri(this, selectedImage, 250);
                            }catch (FileNotFoundException e){
                                e.printStackTrace();
                            }

                            // Coloca a image na image view
                            foodPictureImage.setImageBitmap(imgBitmap);
                        }
                    }
                    break;
            }
        }
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // Chamada quando uma imagem for capturada
        if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null){

            Uri imgUri = data.getData();
            try{
                imgBitmap = ImageUtil.decodeUri(this, imgUri, 250);
                String imageName = userEmail + Calendar.getInstance().getTime().toString();
                imageUrl = ImageUtil.saveToInternalStorage(this, imgBitmap, imageName);
                imgUri = Uri.fromFile(new File(imageUrl + "/" + imageName));
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }

            // Coloca a image na image view
            foodPictureImage.setImageURI(foodImageUri);
        }
    }*/

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
                            foodLocalizationImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_my_location_green_24dp));
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
}
