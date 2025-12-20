package com.example.tourism_service.controller;

import com.example.tourism_service.entity.Destination;
import com.example.tourism_service.service.DestinationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/destinations")
public class DestinationController {

    private final DestinationService destinationService;

    public DestinationController(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    @GetMapping
    public List<Destination> getAllDestinations() {
        return destinationService.getAllDestinations();
    }

    @GetMapping("/{id}")
    public Destination getDestinationById(@PathVariable Long id) {
        return destinationService.getDestinationById(id);
    }

    @PostMapping
    public Destination createDestination(@RequestBody Destination destination) {
        return destinationService.save(destination);
    }

    // Новый: популярные направления
    @GetMapping("/popular")
    public List<Destination> getPopularDestinations(@RequestParam(defaultValue = "5") int limit) {
        return destinationService.getPopularDestinations(limit);
    }
}