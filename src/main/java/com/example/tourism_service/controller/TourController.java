package com.example.tourism_service.controller;

import com.example.tourism_service.entity.Tour;
import com.example.tourism_service.service.TourService;
import jakarta.validation.Valid;  // <-- Важный импорт!
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
        return tourService.getAllTours();
    }

    @GetMapping("/{id}")
    public Tour getTourById(@PathVariable Long id) {
        return tourService.getTourById(id);
    }

    // Поиск по ключевым словам
    @GetMapping("/search")
    public List<Tour> searchTours(@RequestParam String keyword) {
        return tourService.searchTours(keyword);
    }

    // Популярные туры
    @GetMapping("/popular")
    public List<Tour> getPopularTours(@RequestParam(defaultValue = "5") int limit) {
        return tourService.getPopularTours(limit);
    }

    // Рекомендации похожих туров
    @GetMapping("/{id}/recommendations")
    public List<Tour> getRecommendations(
            @PathVariable Long id,
            @RequestParam(defaultValue = "3") int limit) {
        return tourService.getRecommendations(id, limit);
    }

    // Создание нового тура с валидацией
    @PostMapping
    public Tour createTour(@Valid @RequestBody Tour tour) {
        return tourService.saveTour(tour);
    }
}