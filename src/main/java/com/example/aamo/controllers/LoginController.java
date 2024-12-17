package com.example.aamo.controllers;

import com.example.aamo.services.LoginService;
import com.example.aamo.services.MailService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    LoginService loginService;

    @Autowired
    MailService mailService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        if (loginService.authenticateUser(email, password)) {
            return ResponseEntity.ok().body(Map.of("message", "Du er nu logget ind", "status", "success"));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Forkert mail eller kodeord"));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("loggedIn");
        request.getSession().invalidate();
        return ResponseEntity.ok().body(Map.of("message", "Du er nu logget ud"));
    }

    @GetMapping("/session-status")
    public ResponseEntity<?> sessionStatus(HttpSession session) {
        boolean loggedIn = loginService.isUserLoggedIn(session);
        return ResponseEntity.ok().body(Map.of("loggedIn", loggedIn));
    }

    @PostMapping("/forgotten-password")
    public ResponseEntity<?> forgottenPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        if (!mailService.isValidEmail(email)) {
            return ResponseEntity.badRequest().body(Map.of("message", "Forkert mail"));
        }

        if (loginService.findUserByEmail(email) == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "Bruger med denne mail eksisterer ikke"));
        }

        String newPassword = loginService.randomPassword();
        mailService.sendEmail(email, newPassword);
        loginService.updatePassword(newPassword, email);

        return ResponseEntity.ok().body(Map.of("message", "Nyt kodeord er sendt til din mail", "status", "success"));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> request) {
        String currentEmail = request.get("currentEmail");
        String newPassword = request.get("newPassword");
        String repeatedPassword = request.get("repeatedPassword");

        if (loginService.findUserByEmail(currentEmail) == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "Bruger ikke fundet"));
        }

        if (!newPassword.equals(repeatedPassword)) {
            return ResponseEntity.badRequest().body(Map.of("message", "Kodeordene er ikke ens"));
        }

        loginService.updatePassword(newPassword, currentEmail);
        return ResponseEntity.ok().body(Map.of("message", "Kodeordet er opdateret", "status", "success"));
    }


}
