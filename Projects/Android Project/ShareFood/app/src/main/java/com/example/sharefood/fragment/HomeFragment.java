package com.example.sharefood.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.sharefood.activity.CreateFoodPostActivity;
import com.example.sharefood.activity.FoodPostActivity;
import com.example.sharefood.adapter.FoodPostAdapter;
import com.example.sharefood.entity.FoodPost;
import com.example.sharefood.viewmodel.FoodPostViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    EditText searchEditText;
    TextView noMatchFilterText;
    FoodPostViewModel foodPostViewModel;
    RecyclerView recyclerView;
    List<FoodPost> foodPostsList;
    FoodPostAdapter recyclerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        searchEditText = view.findViewById(R.id.search_edit_text);
        noMatchFilterText = view.findViewById(R.id.recycler_empty_text);

        recyclerView = view.findViewById(R.id.food_posts_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setHasFixedSize(true);

        foodPostViewModel = ViewModelProviders.of(getActivity()).get(FoodPostViewModel.class);

        recyclerAdapter = new FoodPostAdapter();

        foodPostsList = new ArrayList<>();
        foodPostsList = foodPostViewModel.getAllFoodPosts();

        recyclerAdapter.setFoodPosts(foodPostsList);
        recyclerAdapter.setOnItemClickListener(new FoodPostAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(FoodPost foodPost) {
                Intent intent = new Intent(getActivity(), FoodPostActivity.class);
                intent.putExtra(Constants.EXTRA_FOOD_POST_ID, foodPost.getId());
                intent.putExtra(Constants.EXTRA_FOOD_POST_NAME, foodPost.getTitulo());
                intent.putExtra(Constants.EXTRA_FOOD_POST_DESCRIPTION, foodPost.getDescricao());
                intent.putExtra(Constants.EXTRA_FOOD_POST_TIME, foodPost.getHorarioParaRetirar());
                intent.putExtra(Constants.EXTRA_FOOD_POST_DATE, foodPost.getDataAberto());
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(recyclerAdapter);

        searchEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP){
                    if(event.getRawX() >= (searchEditText.getRight() - searchEditText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        System.out.println("Pesquisar por voz");

                        return true;
                    }
                }

                return false;
            }
        });

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //after the change calling the method and passing the search input
                filter(editable.toString());
            }
        });

        Button createFoodPostButton = view.findViewById(R.id.create_food_post_button);
        createFoodPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CreateFoodPostActivity.class);
                startActivity(intent);
            }
        });

        getActivity().setTitle("Compartilhe!");

        return view;
    }

    private void filter(String text) {
        List<FoodPost> filteredFoodPosts = new ArrayList<>();

        //looping through existing elements
        for (FoodPost foodPost : foodPostsList) {
            //if the existing elements contains the search input
            if (foodPost.getTitulo().toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                filteredFoodPosts.add(foodPost);
            }
        }

        if(filteredFoodPosts.size() == 0){
            noMatchFilterText.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }else{
            recyclerView.setVisibility(View.VISIBLE);
            noMatchFilterText.setVisibility(View.GONE);
        }

        //calling a method of the adapter class and passing the filtered list
        recyclerAdapter.filterList(filteredFoodPosts);
    }
}
