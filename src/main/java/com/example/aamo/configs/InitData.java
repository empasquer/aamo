package com.example.aamo.configs;

import com.example.aamo.models.ArtWork;
import com.example.aamo.models.Media;
import com.example.aamo.repositories.ArtWorkRepository;
import com.example.aamo.repositories.MediaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class InitData implements CommandLineRunner {

    private final ArtWorkRepository artWorkRepository;
    private final MediaRepository mediaRepository;

    public InitData(ArtWorkRepository artWorkRepository, MediaRepository mediaRepository) {
        this.artWorkRepository = artWorkRepository;
        this.mediaRepository = mediaRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Manually create and save media
        Media media1 = new Media("https://example.com/media1.jpg");
        Media media2 = new Media("https://example.com/media2.jpg");
        Media media3 = new Media("https://example.com/media3.jpg");

        mediaRepository.save(media1);
        mediaRepository.save(media2);
        mediaRepository.save(media3);

        System.out.println("Saved media to the database!");

        // Manually create and save artworks
        List<ArtWork> artworks = new ArrayList<>();
        ArtWork artwork1 = new ArtWork();
        artwork1.setTitle("Beautiful Painting");
        artwork1.setDescription("A beautiful landscape painting");
        artwork1.setPrice(500.00);
        artwork1.setSold(false);
        artwork1.setDateCreated(LocalDateTime.now());
        artwork1.setType(true); // true for painting (maleri)
        artwork1.setMedia(media1);

        artworks.add(artwork1);

        ArtWork artwork2 = new ArtWork();
        artwork2.setTitle("Sculpture of a Woman");
        artwork2.setDescription("A modern sculpture");
        artwork2.setPrice(1500.00);
        artwork2.setSold(false);
        artwork2.setDateCreated(LocalDateTime.now());
        artwork2.setType(false); // false for sculpture
        artwork2.setMedia(media2);

        artworks.add(artwork2);

        ArtWork artwork3 = new ArtWork();
        artwork3.setTitle("Abstract Art");
        artwork3.setDescription("A modern abstract painting");
        artwork3.setPrice(300.00);
        artwork3.setSold(true); // This artwork is sold
        artwork3.setDateCreated(LocalDateTime.now());
        artwork3.setType(true); // true for painting (maleri)
        artwork3.setMedia(media3);

        artworks.add(artwork3);

        for (ArtWork artwork : artworks) {
            artWorkRepository.save(artwork);
            System.out.println("Saved Artwork: " + artwork.getTitle());
        }

        System.out.println("Data initialization completed!");
    }
}