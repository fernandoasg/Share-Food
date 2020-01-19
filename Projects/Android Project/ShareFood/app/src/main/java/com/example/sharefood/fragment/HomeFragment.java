package com.example.sharefood.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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

public class HomeFragment extends Fragment {

    FoodPostViewModel foodPostViewModel;
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.food_posts_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setHasFixedSize(true);

        foodPostViewModel = ViewModelProviders.of(getActivity()).get(FoodPostViewModel.class);

        FoodPostAdapter adapter = new FoodPostAdapter();
        adapter.setFoodPosts(foodPostViewModel.getAllFoodPosts());
        adapter.setOnItemClickListener(new FoodPostAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(FoodPost foodPost) {
                Intent intent = new Intent(getActivity(), FoodPostActivity.class);
                intent.putExtra(Constants.EXTRA_FOOD_POST_NAME, foodPost.getTitulo());
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(adapter);

        final EditText searchEditText = view.findViewById(R.id.search_edit_text);
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
}
