package com.example.teatrnachaynoy;

public class Performance {
    private String title;
    private String image_url;
    private String genre;
    private String duration;
    private String description;
    private String director;
    private StringBuffer actors;

    public Performance(String title, String image_url, String genre, String duration, String description, String director, StringBuffer actors) {
        this.title = title;
        this.image_url = image_url;
        this.genre = genre;
        this.duration = duration;
        this.description = description;
        this.director = director;
        this.actors = actors;
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
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

    public void setDirector(String director) {
        this.director = director;
    }

    public StringBuffer getActors() {
        return actors;
    }

    public void setActors(StringBuffer actors) {
        this.actors = actors;
    }
}
