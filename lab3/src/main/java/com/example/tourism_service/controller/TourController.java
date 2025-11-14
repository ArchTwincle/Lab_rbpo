package com.example.tourism_service.controller;

import com.example.tourism_service.entity.Tour;
import com.example.tourism_service.service.TourService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tours")
public class TourController {
    private final TourService tourService;

    public TourController(TourService tourService) {
        this.tourService = tourService;
    }

    @GetMapping
    public List<Tour> getAllTours() {
        return tourService.findAll();
    }

    @GetMapping("/{id}")
    public Tour getTourById(@PathVariable Long id) {
        return tourService.findById(id);
    }

    @PostMapping
    public Tour createTour(@RequestBody Tour tour) {
        return tourService.save(tour);
    }

    @PutMapping("/{id}")
    public Tour updateTour(@PathVariable Long id, @RequestBody Tour tourDetails) {
        return tourService.update(id, tourDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteTour(@PathVariable Long id) {
        tourService.delete(id);
    }

    @GetMapping("/available")
    public List<Tour> getAvailableTours() {
        return tourService.findAvailableTours();
    }

    @GetMapping("/{id}/availability")
    public boolean checkTourAvailability(@PathVariable Long id) {
        return tourService.isTourAvailable(id);
    }
}