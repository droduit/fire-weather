package com.fire.weather.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class CustomSharedPreference {

    private final static String OWM_API_TOKEN = "OWM_TOKEN";

    private SharedPreferences sharedPref;

    public CustomSharedPreference(Context context) {
        sharedPref = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
    }

    public void setTokenOWM(String token){
        sharedPref.edit().putString(OWM_API_TOKEN, token).apply();
    }

    public String getTokenOWM(){
        return sharedPref.getString(OWM_API_TOKEN, null);
    }

}
