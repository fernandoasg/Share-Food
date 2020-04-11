package com.example.sharefood.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sharefood.Constants;
import com.example.sharefood.R;
import com.example.sharefood.SessionManager;
import com.example.sharefood.back4app.UserParse;
import com.example.sharefood.entity.User;
import com.example.sharefood.viewmodel.UserViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class RegisterActivity extends AppCompatActivity {

    private UserViewModel userViewModel;
    private RadioGroup typeRadioGroup;
    private EditText nomeEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    TextView responseText;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("");
        getSupportActionBar().setElevation(0);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        firebaseAuth = FirebaseAuth.getInstance();

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        typeRadioGroup = findViewById(R.id.radio_group);
        nomeEditText = findViewById(R.id.name_edit_text);
        emailEditText = findViewById(R.id.email_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        responseText = findViewById(R.id.response_text);

        Button registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserRegister();
            }
        });

    }

    private void UserRegister(){

        responseText.setText("");

        final String nome = nomeEditText.getText().toString().trim();
        final String email = emailEditText.getText().toString().trim();
        final String password = passwordEditText.getText().toString().trim();
        final String userType;
        int selectedType = typeRadioGroup.getCheckedRadioButtonId();

        RadioButton radioButton = findViewById(selectedType);
        userType = radioButton.getText().toString();

        if(nome.isEmpty()){
            nomeEditText.setError("Você precisa informar o seu nome");
            nomeEditText.requestFocus();
            return;
        }

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

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    User newUser = new User(nome, email, password, userType);
                    SessionManager sessionManager = new SessionManager(RegisterActivity.this);
                    sessionManager.createSession(String.valueOf(newUser.getId()), newUser.getNome(), newUser.getEmail(), newUser.getUserType());

                    Intent intent = new Intent(getApplicationContext(), RegisterInfoActivity.class);
                    startActivity(intent);
                    finishAffinity();
                }else{
                    System.out.println("Erro: " + task.getException());
                    try{
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        responseText.setText("A senha digitada é muito fraca");
                        System.out.println(e.getMessage());
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        responseText.setText("E-mail inválido");
                        System.out.println(e.getMessage());
                    }catch (FirebaseAuthUserCollisionException e){
                        responseText.setText("E-mail informado já está cadastrado");
                        System.out.println(e.getMessage());
                    } catch (Exception e) {
                        Log.e("Erro", e.getMessage());
                    }
                }
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        return true;
    }
}
