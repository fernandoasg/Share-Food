package com.example.sharefood.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.sharefood.R;
import com.example.sharefood.SessionManager;
import com.example.sharefood.util.ImageUtil;
import com.example.sharefood.viewmodel.FoodPostViewModel;
import com.example.sharefood.viewmodel.InstitutionViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

    TextView openRegisterScreenText;
    Button loginButton;
    EditText emailEditText;
    EditText passwordEditText;
    TextView responseText;
    ProgressBar loadingProgressBar;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.email_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        responseText = findViewById(R.id.response_text);
        loadingProgressBar = findViewById(R.id.loading_progress_bar);

        // Getting current instance of the database
        firebaseAuth = FirebaseAuth.getInstance();

        SessionManager sessionManager = new SessionManager(getApplicationContext());
        if(sessionManager.isLogged()){
            if(sessionManager.registerIsCompleted()){
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finishAffinity();
            }else{
                Intent intent = new Intent(getApplicationContext(), RegisterInfoActivity.class);
                startActivity(intent);
            }
        }

        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                responseText.setText("");

                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if(email.isEmpty()){
                    emailEditText.setError("Você precisa informar o seu e-mail");
                    emailEditText.requestFocus();
                    return;
                }

                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    emailEditText.setError("Você precisa informar um e-mail");
                    emailEditText.requestFocus();
                    return;
                }

                if(password.isEmpty()){
                    passwordEditText.setError("Você precisa informar uma senha");
                    passwordEditText.requestFocus();
                    return ;
                }

                if(password.length() < 6){
                    passwordEditText.setError("A senha precisa ter no mínimo 6 caracteres");
                    passwordEditText.requestFocus();
                    return ;
                }

                loadingProgressBar.setVisibility(View.VISIBLE);

                // Autentica usuário no firebase
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            firestore = FirebaseFirestore.getInstance();

                            final String userId = firebaseAuth.getCurrentUser().getUid();

                            final DocumentReference documentReference = firestore.collection("users").document(userId);
                            documentReference.addSnapshotListener(LoginActivity.this, new EventListener<DocumentSnapshot>() {
                                @Override
                                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                                    System.out.println("documentSnapshot: " + documentSnapshot.getData());
                                    if(documentSnapshot.getData() != null){
                                        String nome = documentSnapshot.getString("name");
                                        final String email = documentSnapshot.getString("email");
                                        boolean giver = documentSnapshot.getBoolean("giver");
                                        boolean info = documentSnapshot.getBoolean("info");

                                        final SessionManager sessionManager = new SessionManager(LoginActivity.this);
                                        sessionManager.createLoginSession(userId, email, nome, giver, info);

                                        if(info){
                                            if(documentSnapshot.getData().containsKey("imageUrl")){
                                                String imageUrl = documentSnapshot.getString("imageUrl");
                                                sessionManager.setImageUrl(imageUrl);

                                                Bitmap bitmap = null;
                                                try {
                                                    bitmap = new DownloadImage().execute(imageUrl).get();
                                                    String url = ImageUtil.saveToInternalStorage(LoginActivity.this, bitmap, email);
                                                    sessionManager.setProfileImagePath(url);
                                                } catch (ExecutionException ex) {
                                                    ex.printStackTrace();
                                                } catch (InterruptedException ex) {
                                                    ex.printStackTrace();
                                                }
                                            }

                                            if(sessionManager.isGiver()){
                                                boolean ehFisica = documentSnapshot.getBoolean("physical");
                                                String document = "";
                                                if(ehFisica)
                                                    document = documentSnapshot.getString("cpf");
                                                else
                                                    document = documentSnapshot.getString("cnpj");
                                                String phone = documentSnapshot.getString("phone");

                                                sessionManager.setDoadorInfo(ehFisica, document, phone);
                                            }else{
                                                String cnpj = documentSnapshot.getString("cnpj");
                                                String responsible = documentSnapshot.getString("responsible");
                                                String phone = documentSnapshot.getString("phone");
                                                String birthday = documentSnapshot.getString("birthday");
                                                String mission = documentSnapshot.getString("mission");

                                                sessionManager.setInstituicaoInfo(cnpj, phone, responsible, birthday, mission);
                                            }

                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                            finishAffinity();
                                        }else{
                                            Intent intent = new Intent(getApplicationContext(), RegisterInfoActivity.class);
                                            startActivity(intent);
                                        }
                                    }else{
                                        //responseText.setText("Erro interno. Tente novamente");
                                    }

                                }
                            });
                        }else{
                            System.out.println("Erro: " + task.getException());
                            try{
                                throw task.getException();
                            }catch (FirebaseAuthInvalidCredentialsException e){
                                responseText.setText("E-mail e/ou senha inválido(s)");
                                System.out.println(e.getMessage());
                            }catch(FirebaseAuthInvalidUserException e){
                                responseText.setText("E-mail não cadastrado");
                                System.out.println(e.getMessage());
                            }catch (Exception e) {
                                Log.e("Erro", e.getMessage());
                            }
                            loadingProgressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }
        });

        openRegisterScreenText = findViewById(R.id.register_link_text);

        openRegisterScreenText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
    
    public static class DownloadImage extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... strings) {
            InputStream inputStream = null;
            Bitmap bitmap = null;
            try {
                inputStream = new java.net.URL(strings[0]).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }
    }
}
