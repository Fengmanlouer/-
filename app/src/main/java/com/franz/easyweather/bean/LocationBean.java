package com.franz.easyweather.bean;

public class LocationBean {
    private String location;
    private String temp;
    private String status;
    private String time;
    public LocationBean(String location,String temp,String status,String time){
        this.location  = location;
        this.temp = temp;
        this.status = status;
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
