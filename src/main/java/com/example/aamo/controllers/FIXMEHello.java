package com.example.aamo.controllers;

import com.example.aamo.repositories.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class FIXMEHello {
    @Autowired
    MediaRepository mediaRepository;

    @GetMapping("/")
    public String index() {
        return "index";
}

    @GetMapping("/hello")
        public String Hello() {
        return "hello";
        }
/* FIXME
    @GetMapping("/testing")
    public String testing(Model model) {
        Media pic = mediaRepository.findById(1).orElseThrow(() -> new IllegalArgumentException("Picture not found"));
        Media vid = mediaRepository.findById(2).orElseThrow(() -> new IllegalArgumentException("Video not found"));
        model.addAttribute("picture", pic);
        model.addAttribute("video", vid);
        return "testing";
    }*/
}
