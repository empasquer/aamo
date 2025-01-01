package com.example.aamo.configs;

import com.example.aamo.models.Event;
import com.example.aamo.models.Media;
import com.example.aamo.repositories.EventRepository;
import com.example.aamo.repositories.MediaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;

@Component
public class InitEvents implements CommandLineRunner {

    private final MediaRepository mediaRepository;
    private final EventRepository eventRepository;

    public InitEvents(MediaRepository mediaRepository, EventRepository eventRepository) {
        this.mediaRepository = mediaRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Create Media
        Media pic1 = new Media("/uploads/50x50/IMG_1283.jpeg");
        Media pic2 = new Media("/uploads/50x50/IMG_1284.jpeg");
        Media pic3 = new Media("/uploads/50x50/IMG_3435.JPG");
        Media pic4 = new Media("/uploads/50x50/IMG_3441.JPG");
        Media pic5 = new Media("/uploads/20x20/IMG_3452.jpg");
        Media pic6 = new Media("/uploads/60x60/IMG_0943.jpeg");

        Media vid1 = new Media("https://www.youtube.com/embed/EShUeudtaFg?si=5Cg_1H8yUQcEfDlI");

        // Create Events
        Event event1 = new Event();
        event1.setStartDate(LocalDate.of(2024, 5, 10));
        event1.setTitle("Kunstudstilling i København");
        event1.setDescription("En smuk udstilling med værker fra forskellige kunstnere, der fremhæver den danske kultur.");

        Event event2 = new Event();
        event2.setStartDate(LocalDate.of(2024, 6, 5));
        event2.setEndDate(LocalDate.of(2024, 6, 8));
        event2.setTitle("Skulpturfestival i Aarhus");
        event2.setDescription("En festival dedikeret til skulpturer fra hele verden, der finder sted i hjertet af Aarhus.");

        Event event3 = new Event();
        event3.setStartDate(LocalDate.of(2021, 3, 4));
        event3.setTitle("Container i Kalumborg");
        event3.setDescription("Udstilling i der fejre 1000år i Kalumborg");

        Event event4 = new Event();
        event4.setStartDate(LocalDate.of(2020, 1, 4));
        event4.setEndDate(LocalDate.of(2020, 1, 8));
        event4.setTitle("2020 vision");
        event4.setDescription("Udstilling i København med tema om fremtiden");

        Event event5 = new Event();
        event5.setStartDate(LocalDate.of(2025, 6, 5));
        event5.setEndDate(LocalDate.of(2025, 6, 8));
        event5.setTitle("Udstilling i London");
        event5.setDescription("Jeg er blevet inviteret til at udstille i London næste år! Temaet er havet. ");

        // Associate Media with Events
        pic1.setEvent(event1);
        pic2.setEvent(event1);
        pic3.setEvent(event2);
        pic4.setEvent(event2);
        pic5.setEvent(event2);
        pic6.setEvent(event4);

        vid1.setEvent(event2);

        // Save Events (CascadeType.ALL will handle saving Media)
        event1.setMediaUrls(Arrays.asList(pic1, pic2));
        event2.setMediaUrls(Arrays.asList(pic3, pic4, pic5, vid1));
        event4.setMediaUrls(Arrays.asList(pic6));

        eventRepository.save(event1);
        eventRepository.save(event2);
        eventRepository.save(event3);
        eventRepository.save(event4);
        eventRepository.save(event5);

        System.out.println("Initialized events with media successfully!");
    }

}
