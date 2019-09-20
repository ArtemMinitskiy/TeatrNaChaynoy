package com.example.teatrnachaynoy;

import android.os.Parcel;
import android.os.Parcelable;

public class Schedule implements Parcelable {
    private String title;
    private String date;
    private String time_and_price;
    private String time_lenght;
    private String link;
    private String image_url;

    public Schedule(String title, String date, String time_and_price, String time_lenght, String link, String image_url) {
        this.title = title;
        this.date = date;
        this.time_and_price = time_and_price;
        this.time_lenght = time_lenght;
        this.link = link;
        this.image_url = image_url;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(this.title);
        parcel.writeString(this.date);
        parcel.writeString(this.time_and_price);
        parcel.writeString(this.time_lenght);
        parcel.writeString(this.link);
        parcel.writeString(this.image_url);
    }

    protected Schedule(Parcel in) {
        this.title = in.readString();
        this.date = in.readString();
        this.time_and_price = in.readString();
        this.time_lenght = in.readString();
        this.link = in.readString();
        this.image_url = in.readString();

    }

    public static final Parcelable.Creator<Schedule> CREATOR = new Parcelable.Creator<Schedule>() {
        @Override
        public Schedule createFromParcel(Parcel source) {
            return new Schedule(source);
        }

        @Override
        public Schedule[] newArray(int size) {
            return new Schedule[size];
        }
    };
}
