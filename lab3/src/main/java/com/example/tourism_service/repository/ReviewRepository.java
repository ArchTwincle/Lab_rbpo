package com.example.tourism_service.repository;

import com.example.tourism_service.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    // Все отзывы для конкретного тура
    List<Review> findByTourId(Long tourId);

    // Отзывы по автору
    List<Review> findByAuthorName(String authorName);

    // Средний рейтинг для тура
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.tour.id = :tourId")
    Double findAverageRatingByTourId(Long tourId);
}