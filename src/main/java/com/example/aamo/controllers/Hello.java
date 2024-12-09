package com.example.aamo.controllers;

import com.example.aamo.repositories.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;


@CrossOrigin(origins = {"http://127.0.0.1:5173", "http://localhost:5173"})
@Controller
public class Hello {
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

    @GetMapping("/testing")
    public String testing(Model model) {

        return "testing";
    }
}
