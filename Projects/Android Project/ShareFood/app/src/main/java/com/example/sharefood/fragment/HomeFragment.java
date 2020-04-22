package com.example.sharefood.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.sharefood.SessionManager;
import com.example.sharefood.activity.CreateFoodPostActivity;
import com.example.sharefood.activity.FoodPostActivity;
import com.example.sharefood.activity.InstitutionActivity;
import com.example.sharefood.adapter.FoodPostAdapter;
import com.example.sharefood.adapter.InstitutionAdapter;
import com.example.sharefood.entity.FoodPost;
import com.example.sharefood.entity.Institution;
import com.example.sharefood.util.ImageUtil;
import com.example.sharefood.viewmodel.FoodPostViewModel;
import com.example.sharefood.viewmodel.InstitutionViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class HomeFragment extends Fragment {

    EditText searchEditText;
    TextView noMatchFilterText;
    RecyclerView recyclerView;

    // Sobre Instituições
    InstitutionViewModel institutionViewModel;
    List<Institution> institutionList;
    InstitutionAdapter institutionAdapter;

    // Sobre Doações
    FoodPostViewModel foodPostViewModel;
    List<FoodPost> foodPostsList;
    FoodPostAdapter foodPostAdapter;

    FirebaseFirestore firestore;
    private int institutionsSize = 0;
    private int foodPostsSize = 0;
    private boolean alreadyListed = false;
    private boolean isGiver = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        searchEditText = view.findViewById(R.id.search_edit_text);
        noMatchFilterText = view.findViewById(R.id.message_recycler_empty_text);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setHasFixedSize(true);

        SessionManager sessionManager = new SessionManager(getContext());
        isGiver = sessionManager.isGiver();
        if(isGiver){
            getActivity().setTitle("Instituições");
            loadInstitionsList(view);

            Button createFoodPostButton = view.findViewById(R.id.create_food_post_button);
            createFoodPostButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), CreateFoodPostActivity.class);
                    startActivity(intent);
                }
            });
        }else{
            loadFoodPostList(view);
            Button createFoodPostButton = view.findViewById(R.id.create_food_post_button);
            createFoodPostButton.setVisibility(View.GONE);
        }

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

        return view;
    }

    private void loadInstitionsList(View view) {

        institutionViewModel = ViewModelProviders.of(getActivity()).get(InstitutionViewModel.class);

        institutionAdapter = new InstitutionAdapter();

        // Pegar instância do Firestore
        firestore = FirebaseFirestore.getInstance();

        /*firestore.collection("users")
                .whereEqualTo("giver", false)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                        if(e != null){
                            Log.w(TAG, "listen:erro", e);
                            return;
                        }
                        for(DocumentChange dc : snapshots.getDocumentChanges()){
                            switch (dc.getType()){
                                case ADDED:
                                    QueryDocumentSnapshot document = dc.getDocument();
                                    String name = document.getString("name");
                                    String responsible = document.getString("responsible");
                                    String cnpj = document.getString("cnpj");
                                    String birthday = document.getString("birthday");
                                    String mission = document.getString("mission");
                                    institutionViewModel.insert(new Institution(name, responsible, cnpj, birthday, mission, 0));

                                    break;
                                case MODIFIED:

                                    break;
                                case REMOVED:
                                    break;
                            }
                        }
                    }
                });
*/

        // TODO Tirar isso aqui depois
        if(institutionViewModel.getAllInstitutionsAmount() > 0){
            //institutionViewModel.deleteAll();
            showInstitutions();
        }else{
            firestore.collection("users")
                .whereEqualTo("giver", false)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            institutionsSize = task.getResult().size();
                            for(QueryDocumentSnapshot document : task.getResult()){
                                String name = document.getString("name");
                                String responsible = document.getString("responsible");
                                String cnpj = document.getString("cnpj");
                                String birthday = document.getString("birthday");
                                String mission = document.getString("mission");

                                String url = null;
                                if(document.getData().containsKey("imageUrl")){
                                    String imageUrl = document.getString("imageUrl");

                                    Bitmap bitmap = null;
                                    try {
                                        bitmap = new ImageUtil.DownloadImage().execute(imageUrl).get();
                                        url = ImageUtil.saveToInternalStorage(getContext(), bitmap, name);
                                        System.out.println("url = " + url);
                                    } catch (ExecutionException ex) {
                                        ex.printStackTrace();
                                    } catch (InterruptedException ex) {
                                        ex.printStackTrace();
                                    }
                                }
                                institutionViewModel.insert(new Institution(name, responsible, cnpj, birthday, mission, 0, url));
                            }
                            showInstitutions();
                        }else{
                            System.out.println("Error getting documents: "+ task.getException());
                        }
                    }
                });
        }
    }

    private void showInstitutions(){

        institutionList = new ArrayList<>();
        institutionList = institutionViewModel.getAllInstitutions();

        institutionAdapter.setInstitutionList(institutionList);
        institutionAdapter.setOnItemClickListener(new InstitutionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Institution institution) {
                Intent intent = new Intent(getActivity(), InstitutionActivity.class);
                intent.putExtra(Constants.EXTRA_INSTITUTION_ID, institution.getId());
                intent.putExtra(Constants.EXTRA_INSTITUTION_NAME, institution.getNome());
                intent.putExtra(Constants.EXTRA_INSTITUTION_RESPONSIBLE, institution.getRepresentante());
                intent.putExtra(Constants.EXTRA_INSTITUTION_BIRTHDAY, institution.getDataCriacao());
                intent.putExtra(Constants.EXTRA_INSTITUTION_MISSION, institution.getMissao());
                intent.putExtra(Constants.EXTRA_INSTITUTION_USER, institution.getUsuarioFk());
                intent.putExtra(Constants.EXTRA_INSTITUTION_PHOTO_PATH, institution.getImageUrl());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(institutionAdapter);
    }

    private void loadFoodPostList(View view) {

        // TODO PEGAR DOAÇÕES ATIVAS DO FIREBASE, IGUAL ESTOU PEGANDO INSTITUIÇÕES
        foodPostViewModel = ViewModelProviders.of(getActivity()).get(FoodPostViewModel.class);

        foodPostAdapter = new FoodPostAdapter();

        // Pegar isntância do Firestore
        firestore = FirebaseFirestore.getInstance();

        if(foodPostViewModel.getAllFoodPostsAmount() > 0){
            showFoodPosts();
        }else{
            firestore.collection("donations")
                .whereEqualTo("active", true)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            foodPostsSize = task.getResult().size();
                            for(QueryDocumentSnapshot document : task.getResult()){
                                String title = document.getString("title");
                                String description = document.getString("description");
                                String timeToTake = document.getString("timeToTake");
                                String dueDate = document.getString("dueDate");
                                String donator = document.getString("donator");
                                double latitude = document.getDouble("latitude");
                                double longitude = document.getDouble("longitude");
                                String publicationDate = document.getString("publicationDate");

                                String url = null;
                                if(document.getData().containsKey("imageUrl")){
                                    String imageUrl = document.getString("imageUrl");
                                    Bitmap bitmap = null;
                                    try{
                                        bitmap = new ImageUtil.DownloadImage().execute(imageUrl).get();
                                        String imageName = donator + publicationDate.replace('/',' ').replaceAll("\\s+","");
                                        url = ImageUtil.saveToInternalStorage(getContext(), bitmap, imageName);
                                    }catch (ExecutionException ex){
                                        ex.printStackTrace();
                                    }catch (InterruptedException ex){
                                        ex.printStackTrace();
                                    }
                                }
                                foodPostViewModel.insert(new FoodPost(title, description, dueDate, publicationDate, timeToTake, longitude, latitude, 0, donator, url));
                            }
                            showFoodPosts();
                        }else{
                            System.out.println("Error getting documents: "+ task.getException());
                        }
                    }
                });
        }
    }

    private void showFoodPosts(){
        foodPostsList = new ArrayList<>();
        foodPostsList = foodPostViewModel.getAllFoodPosts();

        System.out.println(foodPostsList.size());

        foodPostAdapter.setFoodPosts(foodPostsList);
        foodPostAdapter.setOnItemClickListener(new FoodPostAdapter.OnItemClickListener() {
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

        recyclerView.setAdapter(foodPostAdapter);
    }

    private void filter(String text) {
        if(text == null)
            return;

        if(isGiver){
            List<Institution> filteredInstitutions = new ArrayList<>();

            //looping through existing elements
            for (Institution institution : institutionList) {
                //if the existing elements contains the search input
                if (institution.getMissao().toLowerCase().contains(text.toLowerCase())) {
                    //adding the element to filtered list
                    filteredInstitutions.add(institution);
                }
            }

            if(filteredInstitutions.size() == 0){
                noMatchFilterText.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }else{
                recyclerView.setVisibility(View.VISIBLE);
                noMatchFilterText.setVisibility(View.GONE);
            }

            //calling a method of the adapter class and passing the filtered list
            institutionAdapter.filterList(filteredInstitutions);
        }else{
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
            foodPostAdapter.filterList(filteredFoodPosts);
        }

    }
}
