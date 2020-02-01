package com.example.sharefood.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharefood.Constants;
import com.example.sharefood.R;
import com.example.sharefood.activity.FoodStoreActivity;
import com.example.sharefood.adapter.FoodStoreAdapter;
import com.example.sharefood.entity.FoodStore;
import com.example.sharefood.viewmodel.FoodStoreViewModel;

import java.util.ArrayList;
import java.util.List;

public class VendasFragment extends Fragment {

    EditText searchEditText;
    TextView noMatchFilterText;
    FoodStoreViewModel foodStoreViewModel;
    RecyclerView recyclerView;
    List<FoodStore> foodStoreList;
    FoodStoreAdapter recyclerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_stores, container, false);
        getActivity().setTitle("Vendas");

        searchEditText = view.findViewById(R.id.search_store_edit_text);
        noMatchFilterText = view.findViewById(R.id.store_recycler_empty_text);
        recyclerView = view.findViewById(R.id.store_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setHasFixedSize(true);

        foodStoreViewModel = ViewModelProviders.of(getActivity()).get(FoodStoreViewModel.class);
        recyclerAdapter = new FoodStoreAdapter();

        foodStoreList = new ArrayList<>();
        foodStoreList = foodStoreViewModel.getAllFoodStores();

        recyclerAdapter.setFoodStores(foodStoreList);
        recyclerAdapter.setOnItemClickListener(new FoodStoreAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(FoodStore foodStore) {
                Intent intent = new Intent(getActivity(), FoodStoreActivity.class);
                intent.putExtra(Constants.EXTRA_FOOD_STORE_ID, foodStore.getId());
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(recyclerAdapter);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        return view;
    }

    private void filter(String text){
        List<FoodStore> filteredFoodStores = new ArrayList<>();
        for(FoodStore foodStore : foodStoreList){
            if(foodStore.getTitulo().toLowerCase().contains(text.toLowerCase())){
                filteredFoodStores.add(foodStore);
            }
        }

        if(filteredFoodStores.size() == 0){
            noMatchFilterText.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }else{
            recyclerView.setVisibility(View.VISIBLE);
            noMatchFilterText.setVisibility(View.GONE);
        }

        recyclerAdapter.filterList(filteredFoodStores);
    }
}
