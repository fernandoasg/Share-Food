package com.example.sharefood.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.sharefood.R;
import com.example.sharefood.fragment.HomeFragment;
import com.example.sharefood.fragment.MessagesFragment;
import com.example.sharefood.fragment.UserConfigFragment;
import com.example.sharefood.fragment.VendasFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container,
                new HomeFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;

                    switch (menuItem.getItemId()){
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_store:
                            selectedFragment = new VendasFragment();
                            break;
                        case R.id.nav_messages:
                            selectedFragment = new MessagesFragment();
                            break;
                        case R.id.nav_user_options:
                            selectedFragment = new UserConfigFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };
}
