package com.example.aamo.models;

import java.util.List;

public class ArtworkDTO {
    private String title;
    private String description;
    private double price;
    private boolean isSold;
    private boolean type; //maleri eller ej
    List<ArtWorkTag> tags;
    private String mediaUrl;


    public ArtworkDTO(String title, String description, double price,
                      boolean isSold, boolean type, List<ArtWorkTag> tags,
                      String mediaUrl) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.isSold = isSold;
        this.type = type;
        this.tags = tags;
        this.mediaUrl = mediaUrl;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isSold() {
        return isSold;
    }

    public void setSold(boolean sold) {
        isSold = sold;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public List<ArtWorkTag> getTags() {
        return tags;
    }

    public void setTags(List<ArtWorkTag> tags) {
        this.tags = tags;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }
}
