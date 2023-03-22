package com.franz.easyweather.bean;

public class LocationBusBean {
    private String location;
    private int deletePos;
    public LocationBusBean(String location,int deletePos){
        this.location = location;
        this.deletePos = deletePos;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getDeletePos() {
        return deletePos;
    }

    public void setDeletePos(int deletePos) {
        this.deletePos = deletePos;
    }
}
