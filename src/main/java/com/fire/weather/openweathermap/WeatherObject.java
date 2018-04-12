package com.fire.weather.openweathermap;

public class WeatherObject {

    private String id;
    private String main;
    private String description;
    private String icon;

    public WeatherObject(String icon, String description, String main, String id) {
        this.icon = icon;
        this.description = description;
        this.main = main;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getMain() {
        return main;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }
}
