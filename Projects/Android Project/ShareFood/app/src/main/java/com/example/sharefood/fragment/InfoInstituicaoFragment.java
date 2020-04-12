package com.example.sharefood.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.SaveInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sharefood.R;
import com.example.sharefood.SessionManager;
import com.example.sharefood.activity.MainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;


public class InfoInstituicaoFragment extends Fragment {

    EditText cnpjEditText;
    EditText responsibleNameEditText;
    EditText phoneEditText;
    EditText birthDateEditText;
    EditText missionEditText;

    View view;
    FirebaseFirestore firestore;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_info_instituicao, container, false);

        cnpjEditText = view.findViewById(R.id.cnpj_edit_text);
        responsibleNameEditText = view.findViewById(R.id.name_responsible_edit_text);
        phoneEditText = view.findViewById(R.id.phone_edit_text);
        birthDateEditText = view.findViewById(R.id.birth_date_edit_text);
        missionEditText = view.findViewById(R.id.mission_edit_text);

        Button saveButon = view.findViewById(R.id.save_info_button2);
        saveButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveInfo();
            }
        });

        return view;
    }

    private void SaveInfo(){

        final String cnpj = cnpjEditText.getText().toString().trim();
        if(cnpj.isEmpty()){
            cnpjEditText.setError("Você precisa informar o seu CNPJ");
            cnpjEditText.requestFocus();
            return;
        }

        final String responsibleName = responsibleNameEditText.getText().toString().trim();
        if(responsibleName.isEmpty()){
            responsibleNameEditText.setError("Você precisa informar do responsável");
            responsibleNameEditText.requestFocus();
            return;
        }

        final String phone = phoneEditText.getText().toString().trim();
        if(phone.isEmpty()){
            phoneEditText.setError("Você precisa informar um número");
            phoneEditText.requestFocus();
            return;
        }

        final String birthDate = birthDateEditText.getText().toString().trim();
        if(birthDate.isEmpty()){
            birthDateEditText.setError("Você precisa informar a data de criação da Instituição");
            birthDateEditText.requestFocus();
            return;
        }

        final String mission = missionEditText.getText().toString().trim();
        if(mission.isEmpty()){
            missionEditText.setError("Você precisa informar qual é a missão da sua Instituição");
            missionEditText.requestFocus();
            return;
        }

        if(!ehCnpj(cnpj)){
            cnpjEditText.setError("Você precisa informar um CNPJ válido");
            cnpjEditText.requestFocus();
            return;
        }

        Map<String, Object> info = new HashMap<>();
        info.put("cnpj", cnpj);
        info.put("responsible", responsibleName);
        info.put("phone", phone);
        info.put("birthday", birthDate);
        info.put("mission", mission);
        info.put("info", true);

        final SessionManager sessionManager = new SessionManager(getContext());
        String userId = sessionManager.getUserId();

        firestore = FirebaseFirestore.getInstance();
        firestore.collection("users").document(userId)
                .set(info, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                sessionManager.setInstituicaoInfo(cnpj, phone, responsibleName, birthDate, mission);

                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
                getActivity().finishAffinity();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("Erro ao salvar");
            }
        });
    }

    private boolean ehCnpj(String cnpj){
        return true;
    }
}
