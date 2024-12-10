package com.example.aamo.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EventDTO {
    private int eventId;
    private String title;
    private LocalDate startDate;
    @JsonProperty("formattedStartDate") // Ensures this field is included in the response
    private String formattedStartDate;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");


    public EventDTO(int eventId, String title, LocalDate startDate) {
        this.eventId = eventId;
        this.title = title;
        this.startDate = startDate;
    }

    public int getEventId() {
        return eventId;
    }


    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;

    }

    public String getFormattedStartDate() {
        this.formattedStartDate = (startDate != null) ? startDate.format(FORMATTER) : null;
        return formattedStartDate;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getStartDate() {
        return startDate;
    }


}

