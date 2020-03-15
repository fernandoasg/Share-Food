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
    public static final String USER_TYPE = "UserType";
    public static final String USER_TYPE_INFO_SETTED = "UserTypeInfoSetted";

    public SessionManager(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS ,PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createSession(String userId, String userEmail, String userName, String userType){
        editor.putString(USER_ID, userId);
        editor.putString(USER_EMAIL, userEmail);
        editor.putString(USER_NAME, userName);
        editor.putBoolean(USER_LOGGED, true);
        editor.putBoolean(USER_TYPE_INFO_SETTED, false);
        editor.putString(USER_TYPE, userType);
        editor.apply();
    }

    public String getSavedString(String id){
        return sharedPreferences.getString(id, null);
    }

    public void setUserLocation(double latitude, double longitude){
        editor.putFloat(USER_LATITUDE, (float)latitude);
        editor.putFloat(USER_LONGITUDE, (float)longitude);
    }

    // TODO COLOCAR PARAMETROS NECESSÁRIOS
    public void setDoadorInfo(){
        editor.putBoolean(USER_TYPE_INFO_SETTED, true);
    }


    // TODO COLOCAR PARAMETROS NECESSÁRIOS
    public void setInstituicaoInfo(){
        editor.putBoolean(USER_TYPE_INFO_SETTED, true);
    }

    public boolean isLogged(){
        return sharedPreferences.getBoolean(USER_LOGGED, false);
    }

    public boolean registerIsCompleted(){
        return sharedPreferences.getBoolean(USER_TYPE_INFO_SETTED, false);
    }
}
