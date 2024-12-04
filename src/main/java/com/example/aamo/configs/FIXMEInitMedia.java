package com.example.aamo.configs;

import com.example.aamo.models.Media;
import com.example.aamo.repositories.MediaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class FIXMEInitMedia implements CommandLineRunner {

    private MediaRepository mediaRepository;
    public FIXMEInitMedia(MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
    }
    @Override
    public void run(String... args) throws Exception {

        Media pic1 = new Media("https://miro.medium.com/v2/resize:fit:1400/1*Yb2W5n4-ZyQZ8ggggHGCZA.png");
        Media vid1 = new Media("https://www.youtube.com/embed/EShUeudtaFg?si=5Cg_1H8yUQcEfDlI");
/*FIXME
        mediaRepository.save(pic1);
        mediaRepository.save(vid1);*/
    }
}
