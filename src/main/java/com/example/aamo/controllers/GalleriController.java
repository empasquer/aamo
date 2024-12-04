package com.example.aamo.controllers;

import com.example.evaaamo.entities.ArtWork;
import com.example.evaaamo.repositories.ArtWorkRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

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
