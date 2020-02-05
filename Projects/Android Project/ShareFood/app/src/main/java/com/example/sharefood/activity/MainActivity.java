package com.example.sharefood.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.sharefood.R;
import com.example.sharefood.SessionManager;
import com.example.sharefood.fragment.HomeFragment;
import com.example.sharefood.fragment.MessagesFragment;
import com.example.sharefood.fragment.UserConfigFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private FusedLocationProviderClient client;
    private Boolean mLocationPermissionGranted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        client = LocationServices.getFusedLocationProviderClient(this);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container,
                new HomeFragment()).commit();

        getLocationPermission();
        getDeviceLocation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("tO NO RESUME");

        int errorCode = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        switch (errorCode){
            case ConnectionResult.SERVICE_MISSING:
            case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:
            case ConnectionResult.SERVICE_DISABLED:
                GoogleApiAvailability.getInstance().getErrorDialog(this, errorCode,
                        0, new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialogInterface) {
                                finish();
                            }
                        }).show();
                break;
            case ConnectionResult.SUCCESS:
                break;
        }
        System.out.println("VOU ENTRAR NO IF");

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            return;
        }
        System.out.println("asdasdasfasfas");
        client.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if(location != null){
                            Log.i("Teste ", location.getLatitude() + " " + location.getLongitude());
                        }else{
                            Log.i("Teste", "null");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    private void getLocationPermission() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            mLocationPermissionGranted = true;
        }else{
            ActivityCompat.requestPermissions(this,
                    permissions, 1234);
        }
    }

    private void getDeviceLocation() {
        client = LocationServices.
                getFusedLocationProviderClient(this);
        try {
            if(mLocationPermissionGranted){
                final Task location = client.
                        getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Location currentLocation = (Location) task.getResult();
                            double latitude = currentLocation.getLatitude();
                            double longitude = currentLocation.getLongitude();
                            System.out.println("Latitude:"+Double.toString(latitude));
                            System.out.println("Longitude:"+Double.toString(longitude));
                            SessionManager sessionManager = new SessionManager(getApplicationContext());
                            sessionManager.setUserLocation(latitude, longitude);
                        }else{
                            Log.d("TAG","onComplete error found location.");
                        }
                    }
                });
            }
        }catch (SecurityException e){
            Log.d("TAG","onComplete error location is null.");
        }
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
//                        case R.id.nav_store:
//                            selectedFragment = new VendasFragment();
//                            break;
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
