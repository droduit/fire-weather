package com.fire.weather;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fire.weather.helper.Weather;
import com.fire.weather.openweathermap.OpenWeatherObject;
import com.fire.weather.openweathermap.WeatherObject;
import com.github.pavlospt.CircleView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class WeatherActivity extends AppCompatActivity {

    private final static char DEGREE_CHAR = 0x00B0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        try {
            String weatherJsonString = getIntent().getStringExtra("EXTRA_DATA_WEATHER");

            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();

            draw(gson.fromJson(weatherJsonString, OpenWeatherObject.class));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private String getTodayDateInStringFormat(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("E, d MMMM", Locale.getDefault());
        return df.format(c.getTime());
    }

    private String getHoursFromTimestamp(int timestamp) {
        Date date = new java.util.Date(timestamp*1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm");
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(date);
    }

    private void draw(OpenWeatherObject weatherObj) throws JSONException {
        TextView txtCity = findViewById(R.id.city);
        TextView txtDate = findViewById(R.id.current_date);
        TextView txtMin = findViewById(R.id.txtMin);
        TextView txtMax = findViewById(R.id.txtMax);
        TextView txtHumidity = findViewById(R.id.txtHumidity);
        TextView txtWind = findViewById(R.id.txtWind);
        TextView txtSunrise = findViewById(R.id.txtSunrise);
        TextView txtSunset = findViewById(R.id.txtSunset);
        CircleView txtTemp = findViewById(R.id.temp);

        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lparams.gravity = Gravity.CENTER_HORIZONTAL;
        lparams.weight = 1;


        for(WeatherObject weather : weatherObj.getWeather()) {
            String icon = weather.getIcon();

            ImageView imgIcon = new ImageView(this);
            imgIcon.setLayoutParams(lparams);

            TextView txtIcon = new TextView(this);
            txtIcon.setText(weather.getMain());
            txtIcon.setTextColor(Color.WHITE);
            txtIcon.setLayoutParams(lparams);

            LinearLayout weatherLayout = new LinearLayout(this);
            weatherLayout.setOrientation(LinearLayout.VERTICAL);
            weatherLayout.setLayoutParams(lparams);
            weatherLayout.addView(imgIcon);
            weatherLayout.addView(txtIcon);

            LinearLayout iconsLayout = findViewById(R.id.icons);
            iconsLayout.addView(weatherLayout);

            Picasso.get().load("http://openweathermap.org/img/w/" + icon + ".png")
                   .resize(160, 160)
                   .centerCrop().into(imgIcon);
        }

        txtCity.setText(weatherObj.getName()+", "+weatherObj.getSys().getCountry());
        txtTemp.setTitleText(Weather.formatTemp(weatherObj.getMain().getTemp())+DEGREE_CHAR);
        txtMin.setText(Weather.formatTemp(weatherObj.getMain().getTemp_min())+DEGREE_CHAR);
        txtMax.setText(Weather.formatTemp(weatherObj.getMain().getTemp_max())+DEGREE_CHAR);
        txtDate.setText(getTodayDateInStringFormat());
        txtHumidity.setText(weatherObj.getMain().getHumidity());
        txtWind.setText(Weather.formatTemp(weatherObj.getWind().getSpeed()*3.6));
        txtSunrise.setText(getHoursFromTimestamp(weatherObj.getSys().getSunrise()));
        txtSunset.setText(getHoursFromTimestamp(weatherObj.getSys().getSunset()));
    }


}
