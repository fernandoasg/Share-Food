package com.example.sharefood.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.sharefood.Constants;
import com.example.sharefood.R;
import com.example.sharefood.back4app.UserParse;
import com.example.sharefood.entity.User;
import com.example.sharefood.viewmodel.UserViewModel;

public class RegisterActivity extends AppCompatActivity {

    private UserViewModel userViewModel;
    private EditText nomeEditText;
    private EditText emailEditText;
    private EditText celularEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("");
        getSupportActionBar().setElevation(0);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);


        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        nomeEditText = findViewById(R.id.name_edit_text);
        emailEditText = findViewById(R.id.email_edit_text);
        celularEditText = findViewById(R.id.phone_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);

        Button registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserRegister();
            }
        });

    }

    private void UserRegister(){
        String nome = nomeEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String phone = celularEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if(nome.trim().isEmpty()){
            nomeEditText.setError("Você precisa informar o seu nome");
            return;
        }

        if(email.trim().isEmpty()){
            emailEditText.setError("Você precisa informar o seu email");
            return;
        }

        if(phone.trim().isEmpty()){
            celularEditText.setError("Você precisa informar o seu celular");
            return;
        }

        if(password.trim().isEmpty()){
            passwordEditText.setError("Você precisa informar uma senha");
            return ;
        }

        User newUser = new User(nome, email, phone, password);
        userViewModel.cadastraNovoUsuario(newUser);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREFS, MODE_PRIVATE);
                String userId = sharedPreferences.getString(Constants.USER_ID, null);
                System.out.println("id inserido =" + userId);
                if(userId != null){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        }, 1000);


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
