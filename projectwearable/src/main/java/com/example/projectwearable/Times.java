package com.example.projectwearable;


public class Times {
    private String date;
    private String time;
    private int distance;

    public Times() {
    }

    public Times(String date, String time, int distance) {
        this.date = date;
        this.time = time;
        this.distance = distance;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
