package de.bukkitnews.servermanager.controller;

import de.bukkitnews.servermanager.dto.LoginRequest;
import de.bukkitnews.servermanager.dto.RegisterRequest;
import de.bukkitnews.servermanager.model.User;
import de.bukkitnews.servermanager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final @NotNull UserService userService;
    private final @NotNull PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public @NotNull ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        userService.registerUser(request.getUsername(), request.getPassword(), "USER");
        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/login")
    public @NotNull ResponseEntity<String> login(@RequestBody LoginRequest request){
        User user = userService.findByUserName(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid credentials");
        }

        return ResponseEntity.ok("Login successfully");
    }
}
