package com.example.teatrnachaynoy;

public class Schedule {
    private String date;
    private String time;
    private String cost;
    private String time_lenght;
    private String title;
    private String day;

    public Schedule(String date, String time, String cost, String time_lenght, String title, String day) {
        this.date = date;
        this.time = time;
        this.cost = cost;
        this.time_lenght = time_lenght;
        this.title = title;
        this.day = day;
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

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getTime_lenght() {
        return time_lenght;
    }

    public void setTime_lenght(String time_lenght) {
        this.time_lenght = time_lenght;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
