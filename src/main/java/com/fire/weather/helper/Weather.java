package com.fire.weather.helper;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.Locale;

public class Weather {

    private Activity activity;
    private RequestQueue queue;
    private double lat = 46.253746;
    private double lon = 7.453282;

    public Weather(Activity activity, double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
        this.activity = activity;
        queue = Volley.newRequestQueue(activity);
    }

    public void buildJsonObject(final Response.Listener<String> response){
        CustomSharedPreference sharedPref = new CustomSharedPreference(activity);

        String latitude = String.format(Locale.ENGLISH, "%.2f", lat);
        String longitude = String.format(Locale.ENGLISH, "%.2f", lon);
        String apiUrl = "http://api.openweathermap.org/data/2.5/weather?lat="+latitude+"&lon="+longitude+"&units=metric&APPID="+sharedPref.getTokenOWM();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, apiUrl, response, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Volley", "Error " + error.getMessage());
                Toast.makeText(activity.getApplicationContext(), "Error " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        queue.add(stringRequest);
    }

    public static String formatTemp(double temp) {
        return String.format(Locale.ENGLISH, "%.1f", temp);
    }
}


