package com.example.sharefood.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sharefood.Constants;
import com.example.sharefood.R;
import com.example.sharefood.SessionManager;
import com.example.sharefood.util.ImageUtil;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import static android.net.Uri.*;

public class ProfileActivity extends AppCompatActivity {

    // User info fields
    private ImageView userProfileChangeImage;
    private TextView userProfileName;
    private TextView userEmailText;

    // User Address fields
    private EditText cepEditText;
    private EditText logradouroEditText;
    private EditText numeroEditText;
    private EditText estadoEditText;
    private EditText cidadeEditText;
    private EditText complementoEditText;

    StorageReference storageReference;
    private Uri igmUri;
    private UploadTask uploadTask;
    private Bitmap bitmap;

    String userEmail;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userProfileChangeImage = findViewById(R.id.user_profile_change_image);
        userProfileName = findViewById(R.id.user_profile_name);
        userEmailText = findViewById(R.id.user_email_content);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setTitle("Perfil");

        sessionManager = new SessionManager(getApplicationContext());
        String userName = sessionManager.getSavedString(Constants.USER_NAME);
        userEmail = sessionManager.getSavedString(Constants.USER_EMAIL);
        userProfileName.setText(userName);
        userEmailText.setText(userEmail);

        String profileLocalPath = sessionManager.getSavedString(Constants.USER_PROFILE_PHOTO_PATH);
        System.out.println(profileLocalPath);
        if(profileLocalPath != null){
            Bitmap profileBitmap = ImageUtil.loadImageFromStorage(profileLocalPath, userEmail);
            userProfileChangeImage.setImageBitmap(profileBitmap);
            System.out.println("Meu deus setou aqui olha só");
        }

        userProfileChangeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(uploadTask != null && uploadTask.isInProgress())
                    Toast.makeText(ProfileActivity.this, "Uma imagem está sendo salva no momento.", Toast.LENGTH_SHORT).show();
                else
                    ChooseFile();
            }
        });
    }

    private void ChooseFile(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null){
            storageReference = FirebaseStorage.getInstance().getReference("Images");
            igmUri = data.getData();

            try {
                bitmap = ImageUtil.decodeUri(this, igmUri, 250);
                String url = ImageUtil.saveToInternalStorage(this, bitmap, userEmail);
                sessionManager.setProfileImagePath(url);
                igmUri = fromFile(new File(url + "/"+userEmail));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            //userProfileChangeImage.setImageBitmap(bitmap);
            userProfileChangeImage.setImageURI(igmUri);
            UploadImage();
        }
    }

    private void UploadImage(){
        final StorageReference reference = storageReference.child(igmUri.getLastPathSegment());

        uploadTask = (UploadTask) reference.putFile(igmUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!urlTask.isSuccessful());

                        final Uri downloadUrl = urlTask.getResult();

                        // Map para salvar o url
                        Map<String, Object> info = new HashMap<>();
                        info.put("imageUrl", downloadUrl.toString());

                        // Salva o url no documento do usuário atual
                        FirebaseFirestore fireStore = FirebaseFirestore.getInstance();
                        String userId = sessionManager.getUserId();
                        fireStore.collection("users").document(userId)
                        .set(info, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                sessionManager.setImageUrl(downloadUrl.toString());
                                System.out.println("salvou");
                            }
                        });

                        System.out.println(downloadUrl);
                        Toast.makeText(ProfileActivity.this, "A imagem foi salva com sucesso.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });
    }

    private String getImageExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
}
