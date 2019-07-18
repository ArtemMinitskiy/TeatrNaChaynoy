package com.example.teatrnachaynoy;

public class Schedule {
    private String title;
    private String date;
    private String time_and_price;
    private String time_lenght;

    public Schedule(String title, String date, String time_and_price, String time_lenght) {
        this.title = title;
        this.date = date;
        this.time_and_price = time_and_price;
        this.time_lenght = time_lenght;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime_and_price() {
        return time_and_price;
    }

    public void setTime_and_price(String time_and_price) {
        this.time_and_price = time_and_price;
    }

    public String getTime_lenght() {
        return time_lenght;
    }

    public void setTime_lenght(String time_lenght) {
        this.time_lenght = time_lenght;
    }
}
