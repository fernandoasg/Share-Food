package com.example.sharefood;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;

    int PRIVATE_MODE = 0;

    public static final String SHARED_PREFS = "SharedPrefs";
    public static final String USER_ID = "UserId";
    public static final String USER_NAME = "UserName";
    public static final String USER_EMAIL = "UserEmail";
    public static final String USER_LOGGED = "LoggedUser";
    public static final String USER_LATITUDE = "UserLatitude";
    public static final String USER_LONGITUDE = "UserLongitude";

    public SessionManager(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS ,PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createSession(String userId, String userEmail, String userName){
        editor.putString(USER_ID, userId);
        editor.putString(USER_EMAIL, userEmail);
        editor.putString(USER_NAME, userName);
        editor.putBoolean(USER_LOGGED, true);
        editor.apply();
    }

    public void setUserLocation(double latitude, double longitude){
        editor.putFloat(USER_LATITUDE, (float)latitude);
        editor.putFloat(USER_LONGITUDE, (float)longitude);
    }

    public boolean isLogged(){
        return sharedPreferences.getBoolean(USER_LOGGED, false);
    }
}
