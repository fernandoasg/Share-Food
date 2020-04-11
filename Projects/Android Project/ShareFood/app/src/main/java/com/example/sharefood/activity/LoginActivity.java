package com.example.sharefood.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sharefood.R;
import com.example.sharefood.SessionManager;
import com.example.sharefood.viewmodel.FoodPostViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class LoginActivity extends AppCompatActivity {

    TextView openRegisterScreenText;
    Button loginButton;
    EditText emailEditText;
    EditText passwordEditText;
    TextView responseText;
    ProgressBar loadingProgressBar;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.email_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        responseText = findViewById(R.id.response_text);
        loadingProgressBar = findViewById(R.id.loading_progress_bar);

        FoodPostViewModel foodPostViewModel = ViewModelProviders.of(this).get(FoodPostViewModel.class);

        // Getting current instance of the database
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }

        SessionManager sessionManager = new SessionManager(getApplicationContext());
        if(sessionManager.isLogged()){
            if(sessionManager.registerIsCompleted()){
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
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
                            Toast.makeText(LoginActivity.this, "Logado!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finishAffinity();
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
}
