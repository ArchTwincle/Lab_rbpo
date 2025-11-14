package com.example.tourism_service.controller;

import com.example.tourism_service.entity.Review;
import com.example.tourism_service.service.ReviewService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public List<Review> getAllReviews() {
        return reviewService.findAll();
    }

    @GetMapping("/{id}")
    public Review getReviewById(@PathVariable Long id) {
        return reviewService.findById(id);
    }

    @PostMapping
    public Review createReview(@RequestBody Review review) {
        return reviewService.save(review);
    }

    @PutMapping("/{id}")
    public Review updateReview(@PathVariable Long id, @RequestBody Review reviewDetails) {
        return reviewService.update(id, reviewDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable Long id) {
        reviewService.delete(id);
    }

    @GetMapping("/tour/{tourId}")
    public List<Review> getReviewsByTour(@PathVariable Long tourId) {
        return reviewService.findByTourId(tourId);
    }

    @GetMapping("/tour/{tourId}/average-rating")
    public Double getAverageRatingByTour(@PathVariable Long tourId) {
        return reviewService.getAverageRatingByTourId(tourId);
    }
}