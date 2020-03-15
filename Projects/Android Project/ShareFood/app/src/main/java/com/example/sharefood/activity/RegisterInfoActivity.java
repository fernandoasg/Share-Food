package com.example.sharefood.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sharefood.R;
import com.example.sharefood.SessionManager;

public class RegisterInfoActivity extends AppCompatActivity {

    Button saveInfoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_info);
        setTitle("Informações Adicionais");
        getSupportActionBar().setElevation(0);

        saveInfoButton = findViewById(R.id.save_info_button);

        saveInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SessionManager sessionManager = new SessionManager(getApplicationContext());
                if(sessionManager.getSavedString(sessionManager.USER_TYPE).equals("Doador")){
                    sessionManager.setDoadorInfo();
                }else{
                    sessionManager.setInstituicaoInfo();
                }

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });
    }
}
