package com.example.tourism_service.repository;

import com.example.tourism_service.entity.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {

    // Поиск туров по ключевым словам в названии или описании
    @Query("SELECT t FROM Tour t WHERE " +
            "LOWER(t.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(t.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Tour> searchByKeyword(@Param("keyword") String keyword);

    // Для популярных направлений (в DestinationService)
    List<Tour> findByDestinationsContaining(com.example.tourism_service.entity.Destination destination);
}