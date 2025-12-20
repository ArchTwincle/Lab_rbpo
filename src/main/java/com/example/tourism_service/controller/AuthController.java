package com.example.tourism_service.controller;

import com.example.tourism_service.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Map<String, String> request) {
        try {
            String username = request.get("username");
            String password = request.get("password");
            String role = request.get("role"); // ADMIN, USER или GUIDE

            userService.register(username, password, role);
            return ResponseEntity.ok("Пользователь " + username + " успешно создан с ролью " + role);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка: " + e.getMessage());
        }
    }
}