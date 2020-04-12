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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sharefood.R;
import com.example.sharefood.SessionManager;
import com.example.sharefood.activity.MainActivity;
import com.example.sharefood.util.MaskEditUtil;
import com.example.sharefood.util.ValidateUtil;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;


public class InfoDoadorFragment extends Fragment {

    private RadioGroup typeRadioGroup;
    private RadioButton fisicaRadioButton;
    private RadioButton juridicaRadioButton;
    private EditText cpfEditText;
    private EditText cnpjEditText;
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
        cpfEditText = view.findViewById(R.id.cpf_edit_text);
        cpfEditText.addTextChangedListener(MaskEditUtil.mask(cpfEditText, MaskEditUtil.FORMAT_CPF));
        cnpjEditText = view.findViewById(R.id.cnpj2_edit_text);
        cnpjEditText.addTextChangedListener(MaskEditUtil.mask(cnpjEditText, MaskEditUtil.FORMAT_CNPJ));
        phoneEditText = view.findViewById(R.id.phone_edit_text);
        phoneEditText.addTextChangedListener(MaskEditUtil.mask(phoneEditText, MaskEditUtil.FORMAT_FONE));

        // Quando clica no radio button de pessoa física
        fisicaRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cnpjEditText.setVisibility(View.GONE);
                cpfEditText.setVisibility(View.VISIBLE);
                cpfEditText.setText("");
                cpfEditText.requestFocus();
            }
        });

        // Quando clica no radio button de pessoa jurídica
        juridicaRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cpfEditText.setVisibility(View.GONE);
                cnpjEditText.setVisibility(View.VISIBLE);
                cnpjEditText.setText("");
                cnpjEditText.requestFocus();
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
        final boolean ehFisica = doadorType.equals("Física");

        String doc = "";
        if(ehFisica)
            doc = cpfEditText.getText().toString().trim();
        else
            doc = cnpjEditText.getText().toString().trim();

        doc = MaskEditUtil.unmask(doc);
        final String document = doc;

        if(document.isEmpty()){
            if(ehFisica){
                cpfEditText.setError("Você precisa informar o seu CPF");
                cpfEditText.requestFocus();
            }else{
                cnpjEditText.setError("Você precisa informar o seu CNPJ");
                cnpjEditText.requestFocus();
            }

            return;
        }

        final String phone = MaskEditUtil.unmask(phoneEditText.getText().toString().trim());
        if(phone.isEmpty()){
            phoneEditText.setError("Você precisa informar o seu telefone");
            phoneEditText.requestFocus();
            return;
        }

        if(ehFisica){
            if(!ValidateUtil.isCpf(document)){
                cpfEditText.setError("Você precisa informar um CPF válido");
                cpfEditText.requestFocus();

                return;
            }
        }else{
            if(!ValidateUtil.isCnpj(document)){
                cnpjEditText.setError("Você precisa informar um CNPJ válido");
                cnpjEditText.requestFocus();

                return;
            }
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
}
