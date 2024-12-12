package com.example.aamo.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class ArtWorkTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tagId;

    @Enumerated(EnumType.STRING)
    private TagType tagType;

    private String tagValue;

    @ManyToOne
    @JoinColumn(name = "artworkidfk", nullable = true)
    @JsonBackReference
    private ArtWork artWork;

    //Har slettet ArtWork i denne, så man ikke er
    // afhængig af at billedet er gemt og oprettet FØR man laver tags til det.
    public ArtWorkTag(TagType tagType, String tagValue) {
        this.tagType = tagType;
        this.tagValue = tagValue;
    }

    public ArtWorkTag(){

    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public TagType getTagType() {
        return tagType;
    }

    public void setTagType(TagType tagType) {
        this.tagType = tagType;
    }

    public String getTagValue() {
        return tagValue;
    }

    public void setTagValue(String tagValue) {
        this.tagValue = tagValue;
    }

    public ArtWork getArtWork() {
        return artWork;
    }

    public void setArtWork(ArtWork artWork) {
        this.artWork = artWork;
    }
}
