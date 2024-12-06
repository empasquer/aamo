package com.example.aamo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
@CrossOrigin(origins = "http://localhost:5173")
@Controller
public class AboutMeController {

    @GetMapping("/om-mig")
    public String aboutMe() {
        return "about-me";
    }
}
