package com.fire.weather.openweathermap;


public class Main {

    private Double temp;
    private Double pressure;
    private String humidity;
    private Double temp_min;
    private Double temp_max;

    public Main(Double temp, Double pressure, String humidity, Double temp_min, Double temp_max) {
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
    }

    public Double getTemp() {
        return temp;
    }

    public Double getPressure() {
        return pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public Double getTemp_min() {
        return temp_min;
    }

    public Double getTemp_max() {
        return temp_max;
    }
}