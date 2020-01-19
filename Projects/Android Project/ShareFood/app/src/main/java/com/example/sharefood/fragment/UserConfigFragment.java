package com.example.sharefood.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharefood.Constants;
import com.example.sharefood.R;
import com.example.sharefood.adapter.OptionsAdapter;
import com.example.sharefood.entity.Options;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class UserConfigFragment extends Fragment {

    private ArrayList<Options> opcoesDoUsario;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_user_config, container, false);
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
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.SHARED_PREFS, MODE_PRIVATE);
        String userName = sharedPreferences.getString(Constants.USER_NAME, "Nome do Usuário");
        String userEmail = sharedPreferences.getString(Constants.USER_EMAIL, "email@gmail.com");

        Options opcao0 = new Options(userName, userEmail, "perfil", "profile");
        Options opcao1 =  new Options("Alimentos Compartilhados", null, "compartilhado", "SharedFood");
        //Options opcao2 = new Options("Alimentos Adquiridos", null, "recebido", "GotFood");
        Options opcao3 = new Options("Configurações", null, "configuracao", "Config");
        opcoesDoUsario.add(opcao0);
        opcoesDoUsario.add(opcao1);
        //opcoesDoUsario.add(opcao2);
        opcoesDoUsario.add(opcao3);
    }
}
