package com.example.teatrnachaynoy;

public class Performance {
    private String title;
    private String image_url;
    private String description;
    private String director;
    private String director_link;

    public Performance(String title, String image_url, String description, String director, String director_link) {
        this.title = title;
        this.image_url = image_url;
        this.description = description;
        this.director = director;
        this.director_link = director_link;
    }

    public Performance(String image_url) {
        this.image_url = image_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDirector() {
        return director;
    }

    public String getDirector_link() {
        return director_link;
    }

    public void setDirector_link(String director_link) {
        this.director_link = director_link;
    }

    public void setDirector(String director) {
        this.director = director;


    }
}
