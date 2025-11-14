package com.example.tourism_service.repository;

import com.example.tourism_service.entity.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {

    // Упрощенный метод без сложного подзапроса
    @Query("SELECT t FROM Tour t WHERE t.maxParticipants > 0")
    List<Tour> findAvailableTours();

    List<Tour> findByGuideId(Long guideId);
}