package com.example.CourseRegistrationSystem.Controller;

import com.example.CourseRegistrationSystem.Model.User;
import com.example.CourseRegistrationSystem.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
//@CrossOrigin(origins = "https://courseregistry.netlify.app")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public String signup(@RequestParam String username,
                         @RequestParam String password,
                         @RequestParam(defaultValue = "USER") String role) {

        // Check if user already exists
        if (userRepository.findByUsername(username).isPresent()) {
            return "❌ Username already exists!";
        }

        // Encode password before saving
        User newUser = new User(username, passwordEncoder.encode(password), "ROLE_" + role.toUpperCase());
        userRepository.save(newUser);

        return "✅ Account created successfully! You can now login.";
    }
}
