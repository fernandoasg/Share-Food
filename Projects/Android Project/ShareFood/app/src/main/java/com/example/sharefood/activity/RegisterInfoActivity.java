package com.example.sharefood.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.sharefood.R;
import com.example.sharefood.SessionManager;
import com.example.sharefood.fragment.InfoDoadorFragment;
import com.example.sharefood.fragment.InfoInstituicaoFragment;

public class RegisterInfoActivity extends AppCompatActivity {

    SessionManager sessionManager;
    private boolean doubleBackToExitPressedOnce = false;

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
        if(sessionManager.isGiver()){
            getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container,
                    new InfoDoadorFragment()).commit();
        }else{
            getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container,
                    new InfoInstituicaoFragment()).commit();
        }
    }

    @Override
    public void onBackPressed() {
        if(doubleBackToExitPressedOnce){
            super.onBackPressed();
            sessionManager.logout();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Por favor, clique novamente para voltar", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
