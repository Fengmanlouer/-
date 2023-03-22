package com.franz.easyweather.bean;

public class PageBean {
    private int page;
    private String location;
    public PageBean(int page,String location){
        this.page = page;
        this.location = location;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
