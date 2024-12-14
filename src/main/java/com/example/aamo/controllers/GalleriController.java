package com.example.aamo.controllers;

import com.example.aamo.models.ArtWork;
import com.example.aamo.repositories.ArtWorkRepository;
import com.example.aamo.services.JsonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://127.0.0.1:5173", "http://localhost:5173"})
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
       return artWorkRepository.findByTypeTrue();
    }

    @GetMapping("/api/other-works")
    public List<ArtWork> getLatestArtWorkOnlyOther(){
        return artWorkRepository.findByTypeFalse();
    }


    @GetMapping("/api/galleri/about-gallery")
    public Map<String, String> getGalleryDescription() {
        return jsonService.readJsonFile("assets/GalleryDescription.json");
    }

    @DeleteMapping("/api/artworks/{artWorkId}")
    public ResponseEntity<Void> deleteArtWork(@PathVariable int artWorkId) {
        if (artWorkRepository.existsById(artWorkId)) {
            artWorkRepository.deleteById(artWorkId);
            return ResponseEntity.noContent().build(); //Return 204 - No Content
        } else {
            return ResponseEntity.notFound().build(); //Return 404
        }
    }

}
