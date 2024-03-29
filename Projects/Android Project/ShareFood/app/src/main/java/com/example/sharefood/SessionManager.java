package com.example.sharefood;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;

import static com.example.sharefood.Constants.USER_PROFILE_PHOTO_PATH;

public class SessionManager {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;

    int PRIVATE_MODE = 0;

    public static final String SHARED_PREFS = "SharedPrefs";
    public static final String USER_ID = "UserId";
    public static final String USER_NAME = "UserName";
    public static final String USER_EMAIL = "UserEmail";
    public static final String USER_FISICA = "UserFisica";
    public static final String USER_DOCUMENT = "UserDocument";
    public static final String USER_PHONE = "UserPhone";
    public static final String INSTITUTE_RESPONSIBLE = "InstituteResponsible";
    public static final String INSTITUTE_DATE = "InstituteDate";
    public static final String INSTITUTE_MISSION = "InstituteMission";
    public static final String USER_LOGGED = "LoggedUser";
    public static final String USER_LATITUDE = "UserLatitude";
    public static final String USER_LONGITUDE = "UserLongitude";
    public static final String USER_GIVER = "UserGiver";
    public static final String USER_TYPE_INFO_SETTED = "UserTypeInfoSetted";
    public static final String USER_IMAGE_URL = "UserLocalImage";

    public SessionManager(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS ,PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createRegisterSession(String userId, String userEmail, String userName, boolean userGiver){
        editor.putString(USER_ID, userId);
        editor.putString(USER_EMAIL, userEmail);
        editor.putString(USER_NAME, userName);
        editor.putBoolean(USER_LOGGED, true);
        editor.putBoolean(USER_TYPE_INFO_SETTED, false);
        editor.putBoolean(USER_GIVER, userGiver);
        editor.commit();
    }

    public void createLoginSession(String userId, String userEmail, String userName, boolean userGiver, boolean info){
        editor.putString(USER_ID, userId);
        editor.putString(USER_EMAIL, userEmail);
        editor.putString(USER_NAME, userName);
        editor.putBoolean(USER_GIVER, userGiver);
        editor.putBoolean(USER_TYPE_INFO_SETTED, info);
        editor.putBoolean(USER_LOGGED, true);
        editor.commit();
    }

    public void setImageUrl(String imageUrl){
        editor.putString(USER_IMAGE_URL, imageUrl);
        editor.commit();
    }

    public String getUserId(){
        return sharedPreferences.getString(USER_ID, null);
    }

    public boolean isGiver(){
        return sharedPreferences.getBoolean(USER_GIVER, false);
    }

    public String getSavedString(String id){
        return sharedPreferences.getString(id, null);
    }

    public void setUserLocation(double latitude, double longitude){
        editor.putFloat(USER_LATITUDE, (float)latitude);
        editor.putFloat(USER_LONGITUDE, (float)longitude);
        editor.commit();
    }

    public double getLatitude(){
        return sharedPreferences.getFloat(USER_LATITUDE, 0);
    }

    public double getLongitude(){
        return sharedPreferences.getFloat(USER_LONGITUDE, 0);
    }

    public void setDoadorInfo(boolean fisica, String document, String phone){
        editor.putBoolean(USER_FISICA, fisica);
        editor.putString(USER_DOCUMENT, document);
        editor.putString(USER_PHONE, phone);
        editor.putBoolean(USER_TYPE_INFO_SETTED, true);
        editor.commit();
    }

    public void setInstituicaoInfo(String cnpj, String phone, String responsible, String date, String mission){
        editor.putString(USER_DOCUMENT, cnpj);
        editor.putString(USER_PHONE, phone);
        editor.putString(INSTITUTE_RESPONSIBLE, responsible);
        editor.putString(INSTITUTE_DATE, date);
        editor.putString(INSTITUTE_MISSION, mission);
        editor.putBoolean(USER_TYPE_INFO_SETTED, true);
        editor.commit();
    }

    public boolean isLogged(){
        return sharedPreferences.getBoolean(USER_LOGGED, false);
    }

    public boolean registerIsCompleted(){
        return sharedPreferences.getBoolean(USER_TYPE_INFO_SETTED, false);
    }

    public void setProfileImagePath(String path){
        editor.putString(USER_PROFILE_PHOTO_PATH, path);
        editor.commit();
        System.out.println("Setei  " + path);
    }

    public void logout(){
        FirebaseAuth.getInstance().signOut();

        editor.clear();
        editor.commit();
    }
}
