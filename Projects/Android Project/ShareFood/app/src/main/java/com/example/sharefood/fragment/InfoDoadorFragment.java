package com.example.sharefood.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharefood.Constants;
import com.example.sharefood.R;
import com.example.sharefood.SessionManager;
import com.example.sharefood.activity.MainActivity;
import com.example.sharefood.activity.MessageActivity;
import com.example.sharefood.adapter.MessageAdapter;
import com.example.sharefood.entity.Message;
import com.example.sharefood.viewmodel.MessageViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class InfoDoadorFragment extends Fragment {

    private RadioGroup typeRadioGroup;
    private RadioButton fisicaRadioButton;
    private RadioButton juridicaRadioButton;
    private EditText cpfOrCnpjEditText;
    private EditText phoneEditText;

    private View view;

    FirebaseFirestore firestore;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_info_doador, container, false);

        typeRadioGroup = view.findViewById(R.id.doador_type_radio_group);
        fisicaRadioButton = view.findViewById(R.id.fisica_radio_button);
        juridicaRadioButton = view.findViewById(R.id.juridica_radio_button);
        cpfOrCnpjEditText = view.findViewById(R.id.cpf_cnpj_edit_text);
        phoneEditText = view.findViewById(R.id.phone_edit_text);

        // Quando clica no radio button de pessoa física
        fisicaRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cpfOrCnpjEditText.setText("");
                cpfOrCnpjEditText.setHint("CPF");
                cpfOrCnpjEditText.requestFocus();
            }
        });

        // Quando clica no radio button de pessoa jurídica
        juridicaRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cpfOrCnpjEditText.setText("");
                cpfOrCnpjEditText.setHint("CNPJ");
                cpfOrCnpjEditText.requestFocus();
            }
        });


        Button saveButton = view.findViewById(R.id.save_info_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveInfo();
            }
        });

        return view;
    }

    private void SaveInfo(){
        int selectedType = typeRadioGroup.getCheckedRadioButtonId();
        System.out.println(selectedType);
        RadioButton radioButton = view.findViewById(selectedType);
        String doadorType = radioButton.getText().toString();

        final String document = cpfOrCnpjEditText.getText().toString().trim();
        if(document.isEmpty()){
            cpfOrCnpjEditText.setError("Você precisa informar o seu documento");
            cpfOrCnpjEditText.requestFocus();
            return;
        }

        final String phone = phoneEditText.getText().toString().trim();
        if(phone.isEmpty()){
            phoneEditText.setError("Você precisa informar o seu telefone");
            phoneEditText.requestFocus();
            return;
        }

        final boolean ehFisica = doadorType.equals("Física");
        if(!isValidDocument(ehFisica, document)){
            cpfOrCnpjEditText.setError("Você precisa informar um documento válido");
            cpfOrCnpjEditText.requestFocus();
            return;
        }

        Map<String, Object> info = new HashMap<>();
        info.put("physical", ehFisica);
        if(ehFisica)
            info.put("cpf", document);
        else
            info.put("cnpj", document);

        info.put("phone", phone);
        info.put("info", true);

        final SessionManager sessionManager = new SessionManager(getContext());
        String userId = sessionManager.getUserId();

        firestore = FirebaseFirestore.getInstance();
        firestore.collection("users").document(userId)
                .set(info, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                sessionManager.setDoadorInfo(ehFisica, document, phone);

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

    private boolean isValidDocument(boolean ehFisica, String document){
        return true;
    }
}
