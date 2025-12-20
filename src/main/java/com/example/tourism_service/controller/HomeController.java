package com.example.tourism_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Добро пожаловать в Tourism Service API! " +
                "Доступные endpoints: /api/guides, /api/destinations, /api/tours, /api/bookings, /api/reviews";
    }
}