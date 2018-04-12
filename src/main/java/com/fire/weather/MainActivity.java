package com.fire.weather;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Response;
import com.fire.weather.helper.CustomSharedPreference;
import com.fire.weather.helper.LocationTrack;
import com.fire.weather.helper.Weather;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class MainActivity extends AppCompatActivity {

    private final static int REQUEST_LOCATION = 200;

    private Button btPermission;
    private LocationTrack locationTrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }

        CustomSharedPreference sharedPref = new CustomSharedPreference(MainActivity.this);
        sharedPref.setTokenOWM("babfac0fd303a578be6782df942d6a4e");

        btPermission = findViewById(R.id.btPermission);
        btPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!hasPermissionLocation()) {
                    requestPermissionLocation();
                }
            }
        });

        if (!hasPermissionLocation()) {
            requestPermissionLocation();
        } else {
            getWeatherAndSwitch();
        }

    }

    private void requestPermissionLocation() {
        String[] permissions = new String[]{ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION};
        ActivityCompat.requestPermissions(MainActivity.this, permissions, REQUEST_LOCATION);
    }

    private boolean hasPermissionLocation() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return checkSelfPermission(ACCESS_FINE_LOCATION) == PERMISSION_GRANTED &&
                       checkSelfPermission(ACCESS_COARSE_LOCATION) == PERMISSION_GRANTED;
            }
        }
        return true;
    }

    private void getWeatherAndSwitch() {
        locationTrack = new LocationTrack(MainActivity.this);
        if (locationTrack.canGetLocation()) {
            double longitude = locationTrack.getLongitude();
            double latitude = locationTrack.getLatitude();

            Log.d("Position", "lat:" + latitude + ", lon:" + longitude);

            Weather weather = new Weather(this, latitude, longitude);
            weather.buildJsonObject(new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (null == response) {
                        Toast.makeText(getApplicationContext(), "Error: Nothing was returned from openweathermap.com", Toast.LENGTH_LONG).show();
                    } else {
                        Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
                        intent.putExtra("EXTRA_DATA_WEATHER", response);
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        MainActivity.this.finish();
                    }
                }
            });
        } else {
            locationTrack.showSettingsAlert();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_LOCATION) {
            if (grantResults[0] == PERMISSION_GRANTED && grantResults[1] == PERMISSION_GRANTED) {
                if (hasPermissionLocation()) {
                    btPermission.setVisibility(View.GONE);
                    getWeatherAndSwitch();
                }
            } else {
                btPermission.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(locationTrack != null)
            locationTrack.stopListener();
    }

}
