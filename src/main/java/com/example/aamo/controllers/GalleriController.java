package com.example.aamo.controllers;

import com.example.aamo.models.ArtWork;
import com.example.aamo.repositories.ArtWorkRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
@Controller
public class GalleriController {
    private ArtWorkRepository artWorkRepository;

    public GalleriController(ArtWorkRepository artWorkRepository){
        this.artWorkRepository = artWorkRepository;
    }

    @GetMapping("/galleri")
    public List<ArtWork> getArtWorks() {
        return artWorkRepository.findAll();
    }
}
