package com.example.aamo.controllers;

import com.example.aamo.models.ArtWork;
import com.example.aamo.repositories.ArtWorkRepository;
import com.example.aamo.services.JsonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class GalleriController {
    @Autowired
    private ArtWorkRepository artWorkRepository;
    @Autowired
    private JsonService jsonService;

    public GalleriController(ArtWorkRepository artWorkRepository){
        this.artWorkRepository = artWorkRepository;
    }

    @GetMapping("/api/galleri")
    public List<ArtWork> getLatestArtWorkOnlyPaintings(){
       return artWorkRepository.findAll();

        /* return artWorkRepository.findAll().stream()
                .filter(artWork -> artWork.isType())
                .sorted((a1, a2) -> Long.compare(a2.getArtWorkId(), a1.getArtWorkId()))
                .toList();

         */
    }




    @GetMapping("/api/galleri/about-gallery")
    public Map<String, String> getGalleryDescription() {
        return jsonService.readJsonFile("assets/GalleryDescription.json");
    }




   /* @GetMapping("/api/galleri")
    public Map<String, Object> getGalleriData() {
        List<ArtWork> artWorks = artWorkRepository.findAll();
        Map<String, String> galleryDescription = jsonService.readJsonFile("assets/galleryDescription.json");

        // Kombiner data i en enkelt respons
        return Map.of(
                "artWorks", artWorks,
                "galleryDescription", galleryDescription.get("description")
        );
    }

    */

}
