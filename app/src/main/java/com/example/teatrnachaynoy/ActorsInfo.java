package com.example.teatrnachaynoy;

public class ActorsInfo {
    private String name;
    private String charecter;
    private String description;
    private String partOfPerf;
    private String image;
    private String link;

    public ActorsInfo() {
    }

    public ActorsInfo(String name, String link) {
        this.name = name;
        this.link = link;
    }

    public ActorsInfo(String name, String image, String description, String partOfPerf, String link) {
        this.name = name;
        this.description = description;
        this.partOfPerf = partOfPerf;
        this.image = image;
        this.link = link;
    }

    public ActorsInfo(String charecter, String name, String image, String link) {
        this.charecter = charecter;
        this.name = name;
        this.image = image;
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCharecter() {
        return charecter;
    }

    public void setCharecter(String charecter) {
        this.charecter = charecter;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPartOfPerf() {
        return partOfPerf;
    }

    public void setPartOfPerf(String partOfPerf) {
        this.partOfPerf = partOfPerf;
    }
}
