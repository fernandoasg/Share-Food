package com.example.sharefood.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sharefood.R;
import com.example.sharefood.SessionManager;
import com.example.sharefood.fragment.InfoDoadorFragment;
import com.example.sharefood.fragment.InfoInstituicaoFragment;

public class RegisterInfoActivity extends AppCompatActivity {

    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_info);
        setTitle("Informações Adicionais");
        getSupportActionBar().setElevation(0);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        sessionManager = new SessionManager(this);
        if(sessionManager.getSavedString(sessionManager.USER_TYPE).equals("Doador")){
            getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container,
                    new InfoDoadorFragment()).commit();
        }else{
            getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container,
                    new InfoInstituicaoFragment()).commit();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        sessionManager.logout();
    }
}
