package com.fire.weather.openweathermap;

public class Wind {

    private Double speed;
    private String deg;

    public Wind(Double speed, String deg) {
        this.speed = speed;
        this.deg = deg;
    }

    public Double getSpeed() {
        return speed;
    }

    public String getDeg() {
        return deg;
    }
}
