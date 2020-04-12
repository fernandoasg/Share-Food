package com.example.sharefood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sharefood.Constants;
import com.example.sharefood.R;

public class InstitutionActivity extends AppCompatActivity {

    TextView institutionNameText;
    TextView institutionResponsibleNameText;
    TextView institutionBirthDateText;
    TextView institutionMissionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_institution);

        institutionNameText = findViewById(R.id.institution_name_text);
        institutionResponsibleNameText = findViewById(R.id.institution_responsible_name_text);
        institutionBirthDateText = findViewById(R.id.institution_birth_date_text);
        institutionMissionText = findViewById(R.id.institution_mission_text);
        getSupportActionBar().setElevation(0);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();

        if(intent.hasExtra(Constants.EXTRA_INSTITUTION_ID)){
            String institutionName = intent.getStringExtra(Constants.EXTRA_INSTITUTION_NAME);
            institutionNameText.setText(institutionName);
            setTitle(institutionName);

            String institutionResponsibleName = intent.getStringExtra(Constants.EXTRA_INSTITUTION_RESPONSIBLE);
            institutionResponsibleNameText.setText("Responsável: " + institutionResponsibleName);

            String institutionBirthDate = intent.getStringExtra(Constants.EXTRA_INSTITUTION_BIRTHDAY);
            institutionBirthDateText.setText("Fundada em: " + institutionBirthDate);

            String institutionMission = intent.getStringExtra(Constants.EXTRA_INSTITUTION_MISSION);
            institutionMissionText.setText(institutionMission);
        }
    }
}
