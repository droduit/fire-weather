package com.fire.weather.openweathermap;

import java.util.List;

public class OpenWeatherObject {

    private List<WeatherObject> weather;
    private Main main;
    private Wind wind;
    private Sys sys;
    private String id;
    private String name;

    public OpenWeatherObject(List<WeatherObject> weather, Main main, Wind wind, Sys sys, String id, String name) {
        this.weather = weather;
        this.main = main;
        this.wind = wind;
        this.sys = sys;
        this.id = id;
        this.name = name;
    }

    public List<WeatherObject> getWeather() {
        return weather;
    }
    public Main getMain() {
        return main;
    }
    public Wind getWind() {
        return wind;
    }
    public Sys getSys() {
        return sys;
    }
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }

}
