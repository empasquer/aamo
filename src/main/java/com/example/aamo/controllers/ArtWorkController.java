package com.example.aamo.controllers;

import com.example.aamo.models.ArtWork;
import com.example.aamo.models.ArtWorkTag;
import com.example.aamo.models.ArtworkDTO;
import com.example.aamo.models.TagType;
import com.example.aamo.repositories.ArtWorkRepository;
import com.example.aamo.repositories.ArtWorkTagRepository;
import com.example.aamo.services.ArtworkService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = {"http://127.0.0.1:5173", "http://localhost:5173"})
@RestController
public class ArtWorkController {
    private ArtWorkRepository artWorkRepository;
    private ArtWorkTagRepository artWorkTagRepository;
    private ArtworkService artworkService;

    public ArtWorkController(ArtWorkRepository artWorkRepository, ArtWorkTagRepository artWorkTagRepository, ArtworkService artworkService){
        this.artWorkRepository = artWorkRepository;
        this.artWorkTagRepository = artWorkTagRepository;
        this.artworkService = artworkService;
    }


    @GetMapping("/artworks")
    public List<ArtWork> getArtWorks() {
        System.out.println("HELLO /artworks has been through ghere");
        return artWorkRepository.findAll();
    }

    @GetMapping("/test")
    public String test() {
        return "Test endpoint is working!";
    }
    @GetMapping("api/tags")
    public List<ArtWorkTag> getTags() {
        return artWorkTagRepository.findAll();
    }
    @PostMapping("api/addArtwork")
    public void addArtwork(@RequestBody ArtworkDTO artworkDTO) {
        artworkService.saveArtworkWithTagsAndPicture(artworkDTO);
    }
    @GetMapping("api/saveArtwork")
    public ResponseEntity saveArtwork() {
        List<ArtWorkTag> tags =  List.of(
                new ArtWorkTag(TagType.SIZE, "50x50"),
                new ArtWorkTag(TagType.THEME, "Damer"),
                new ArtWorkTag(TagType.COLOR, "Rød")
        );
        ArtworkDTO artworkDTO = new ArtworkDTO("test", "test",100, false, true, tags, "/eva.png");
        artworkService.saveArtworkWithTagsAndPicture(artworkDTO);
        return ResponseEntity.ok("works");
    }
}
