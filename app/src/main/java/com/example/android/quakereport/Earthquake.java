package com.example.android.quakereport;

import java.util.Date;

/**
 * Created by Rishabh on 16-05-2017.
 */

public class Earthquake {
    private Double magnitude;
    private String location;
    private String date;
    private String time;
    private String url;

    public Earthquake(Double mag, String city, String up_date, String time_user,String urlstr) {
        magnitude = mag;
        location = city;
        time = time_user;
        date = up_date;
        url = urlstr;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(Double magnitude) {
        this.magnitude = magnitude;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
