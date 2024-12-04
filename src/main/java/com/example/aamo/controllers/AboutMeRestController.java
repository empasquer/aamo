package com.example.aamo.controllers;

import com.example.evaaamo.entities.Event;
import com.example.evaaamo.repositories.EventRepository;
import com.example.evaaamo.services.JsonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class AboutMeRestController {
    @Autowired
    JsonService jsonService;
    @Autowired
    EventRepository eventRepository;

@GetMapping("/api/about")
    public Map<String, String> getAbout() {
    return jsonService.readJsonFile("static/assets/about.json");
}

@GetMapping("/api/event")
    public Event getEvent(@RequestParam int eventId) {
    Event event = eventRepository.findById(eventId).orElseThrow();
    return event;
}

@GetMapping("/api/events")
    public List<Event> getAllEvents() {
    List<Event> events = eventRepository.findAll();
    return events;
}

}
