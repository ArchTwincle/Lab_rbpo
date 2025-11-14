package com.example.tourism_service.service;

import com.example.tourism_service.entity.Review;
import com.example.tourism_service.repository.ReviewRepository;
import com.example.tourism_service.repository.BookingRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final BookingRepository bookingRepository;

    public ReviewService(ReviewRepository reviewRepository, BookingRepository bookingRepository) {
        this.reviewRepository = reviewRepository;
        this.bookingRepository = bookingRepository;
    }

    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    public Review findById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found with id: " + id));
    }

    public Review save(Review review) {
        // Проверяем, был ли клиент на этом туре
        boolean hasBooked = bookingRepository.existsByClientNameAndTourIdAndTourEndDateBefore(
                review.getAuthorName(),
                review.getTour().getId(),
                LocalDate.now()
        );

        if (!hasBooked) {
            throw new RuntimeException("Only participants of past tours can leave reviews");
        }

        review.setReviewDate(LocalDate.now());
        return reviewRepository.save(review);
    }

    public Review update(Long id, Review reviewDetails) {
        Review review = findById(id);
        review.setAuthorName(reviewDetails.getAuthorName());
        review.setContent(reviewDetails.getContent());
        review.setRating(reviewDetails.getRating());
        review.setTour(reviewDetails.getTour());
        return reviewRepository.save(review);
    }

    public void delete(Long id) {
        reviewRepository.deleteById(id);
    }

    public List<Review> findByTourId(Long tourId) {
        return reviewRepository.findByTourId(tourId);
    }

    public Double getAverageRatingByTourId(Long tourId) {
        return reviewRepository.findAverageRatingByTourId(tourId);
    }
}