package com.example.aamo.models;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;




@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int eventId;
    @Nonnull
    LocalDate startDate;
    @Nullable
    LocalDate endDate;
    @Nullable
    String title;
    @Nullable
    String description;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Media> mediaUrls;


    @Transient // Prevents this field from being persisted
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Transient
    private String formattedStartDate;

    @Transient
    private String formattedEndDate;

    public Event(int eventId, @Nonnull LocalDate startDate, @Nullable LocalDate endDate, @Nullable String title, @Nullable String description, List<Media> mediaUrls) {
        this.eventId = eventId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.description = description;
        this.mediaUrls = mediaUrls;
    }

    public Event(@Nonnull LocalDate startDate, @Nullable LocalDate endDate, @Nullable String title, @Nullable String description) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.description = description;
    }

    public Event() {
    }

    public int getEventId() {
        return eventId;
    }

    @Nonnull
    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(@Nonnull LocalDate startDate) {
        this.startDate = startDate;
    }

    @Nullable
    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(@Nullable LocalDate endDate) {
        this.endDate = endDate;
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    public void setTitle(@Nullable String title) {
        this.title = title;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    public List<Media> getMediaUrls() {
        return mediaUrls;
    }

    public void setMediaUrls(List<Media> mediaUrls) {
        this.mediaUrls = mediaUrls;
    }


    @Nonnull
    public String getFormattedStartDate() {
        return formattedStartDate;
    }

    @Nullable
    public String getFormattedEndDate() {
        return formattedEndDate;
    }

    @PostLoad
    private void formatDatesAfterLoad() {
        // Convert LocalDate to formatted strings after loading from the database
        this.formattedStartDate = startDate.format(FORMATTER);
        this.formattedEndDate = (endDate != null) ? endDate.format(FORMATTER) : null;
    }

    @PrePersist
    private void normalizeDatesBeforeSave() {
        // Convert formatted strings back to LocalDate before saving
        if (formattedStartDate != null) {
            this.startDate = LocalDate.parse(formattedStartDate, FORMATTER);
        }
        if (formattedEndDate != null) {
            this.endDate = LocalDate.parse(formattedEndDate, FORMATTER);
        }
    }

    @PreUpdate
    private void updateDatesBeforeSave() {
        // Convert formatted strings back to LocalDate before updating
        if (formattedStartDate != null) {
            this.startDate = LocalDate.parse(formattedStartDate, FORMATTER);
        }
        if (formattedEndDate != null) {
            this.endDate = LocalDate.parse(formattedEndDate, FORMATTER);
        }
    }
}


