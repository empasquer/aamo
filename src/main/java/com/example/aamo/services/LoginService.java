package com.example.aamo.services;

/*import com.example.aamo.models.User;
import com.example.aamo.repositories.LoginRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Random;

@Service
public class LoginService {

    @Autowired
    LoginRepository loginRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    HttpSession session;

    public User findUserByEmail(String email) {
        return loginRepository.findUserByEmail(email);
    }

    public boolean authenticateUser(String email, String password) {
        User user = findUserByEmail(email);

        System.out.println("Mail used: " + email);
        System.out.println("Password used: " + password);
        if (user != null) {
            String storedPassword = user.getPassword();
            if (verifyPassword(password, storedPassword)) {
                session.setAttribute("loggedIn", true);
                System.out.println("succes");
                return true; //Authentication successful
            } else {
                System.out.println("Password mismatch");
                return false; //Password mismatch
            }
        } else {
            System.out.println("User not found");
            return false; //User not found
        }
    }

    public String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public boolean verifyPassword(String password, String hashedPassword) {
        return passwordEncoder.matches(password, hashedPassword);
    }

    public boolean isUserLoggedIn(HttpSession session) {
        return session.getAttribute("loggedIn") != null;
    }

    public String randomPassword() {
        String validChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(validChars.length());
            password.append(validChars.charAt(index));
        }
        return password.toString();
    }

    @Transactional //Needed so it can roll back in case there is an error when updating
    public void updatePassword(String password, String email) {
        String hashedPassword = hashPassword(password);
        loginRepository.updatePasswordByEmail(email, hashedPassword);
    }

}

 */
