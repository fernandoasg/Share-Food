package com.example.sharefood;

//import com.parse.Parse;
import android.app.Application;

public class ShareFood extends Application {

    // Initializes ParseSDK as soon as the application is created
    @Override
    public void onCreate(){
        super.onCreate();

        /*Parse.initialize(new Parse.Configuration.Builder(this)
            .applicationId("zHDQHmaFTZv1kyAN0NuwRVikQP4rzE198yn6rntz")
            .clientKey("SAyvMub3LA2uq2gD9qjyRKllIJ6WenIqtEywVyHT")
            .server("https://parseapi.back4app.com")
            .build());*/
    }
}
