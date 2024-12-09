package com.example.aamo.controllers;
import com.example.aamo.models.Event;
import com.example.aamo.models.EventDTO;
import com.example.aamo.repositories.EventRepository;
import com.example.aamo.services.JsonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import com.example.aamo.services.JsonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@CrossOrigin(origins = {"http://127.0.0.1:5173", "http://localhost:5173"})
@RestController
public class AboutMeRestController {
    @Autowired
    JsonService jsonService;
    @Autowired
    EventRepository eventRepository;

    @GetMapping("/api/about")
    public Map<String, String> getAbout() {
        return jsonService.readJsonFile("assets/about.json");
    }

    @GetMapping("/api/event")
    public Event getEvent(@RequestParam int eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow();
        return event;
    }
    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/api/events")
    public List<EventDTO> getAllEvents() {
        List<EventDTO> events = eventRepository.findEventSummary();
        return events;
    }

}
