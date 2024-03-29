package com.example.teatrnachaynoy;

public class Repertoire {
    private String image_url;
    private String title;
    private String description;
    private String link;

    public Repertoire() {
    }

    public Repertoire(String image_url, String title, String description, String link) {
        this.image_url = image_url;
        this.title = title;
        this.description = description;
        this.link = link;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
