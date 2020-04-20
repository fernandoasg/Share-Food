package com.example.sharefood.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharefood.Constants;
import com.example.sharefood.R;
import com.example.sharefood.SessionManager;
import com.example.sharefood.activity.LoginActivity;
import com.example.sharefood.adapter.OptionsAdapter;
import com.example.sharefood.entity.Options;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class UserConfigFragment extends Fragment {

    private ArrayList<Options> opcoesDoUsario;
    TextView exitTextButton;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_user_config, container, false);
        getActivity().setTitle("Configurações");

        exitTextButton = view.findViewById(R.id.exit_text_button);
        exitTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), LoginActivity.class));
                getActivity().finishAffinity();
                SessionManager sessionManager = new SessionManager(getContext());
                sessionManager.logout();
            }
        });

        opcoesDoUsario = new ArrayList<>();
        RecyclerView userOptionsRecyclerView = view.findViewById(R.id.user_options_recycler_view);

        GenerateOptions();
        OptionsAdapter optionsAdapter = new OptionsAdapter(opcoesDoUsario);

        userOptionsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        userOptionsRecyclerView.setHasFixedSize(true);
        userOptionsRecyclerView.setAdapter(optionsAdapter);

        return view;
    }

    private void GenerateOptions(){
        SessionManager sessionManager = new SessionManager(getContext());
        String profileLocalPath = sessionManager.getSavedString(Constants.USER_PROFILE_PHOTO_PATH);
        String userName = sessionManager.getSavedString(Constants.USER_NAME);
        String userEmail = sessionManager.getSavedString(Constants.USER_EMAIL);

        Options opcao0 = new Options(userName, userEmail, profileLocalPath, "Profile");
        Options opcao1 =  new Options("Alimentos Compartilhados", null, "compartilhado", "SharedFood");
        //Options opcao3 = new Options("Configurações", null, "configuracao", "Configuration");

        opcoesDoUsario.add(opcao0);
        opcoesDoUsario.add(opcao1);
        //opcoesDoUsario.add(opcao3);
    }
}
