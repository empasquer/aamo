package com.example.aamo.controllers;

import com.example.aamo.models.DELKommune;
import com.example.aamo.repositories.DELKommuneRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DELKommuneController {
    private DELKommuneRepository DELKommuneRepository;

    public DELKommuneController(DELKommuneRepository DELKommuneRepository) {
        this.DELKommuneRepository = DELKommuneRepository;
    }

    @GetMapping("/kommuner")
    public List<DELKommune> getByRegionskode(@RequestParam String regionskode) {
        return DELKommuneRepository.findByRegion_Kode(regionskode);
    }
}
