package com.example.aamo.controllers;

import com.example.aamo.models.ArtWork;
import com.example.aamo.repositories.ArtWorkRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class ArtWorkController {
    private ArtWorkRepository artWorkRepository;

    public ArtWorkController(ArtWorkRepository artWorkRepository){
        this.artWorkRepository = artWorkRepository;
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
}
