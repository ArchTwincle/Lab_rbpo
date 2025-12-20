package com.example.tourism_service.repository;

import com.example.tourism_service.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Все бронирования для конкретного тура
    List<Booking> findByTourId(Long tourId);

    // Подсчет бронирований для тура
    int countByTourId(Long tourId);

    // Проверка, был ли клиент на туре (для отзывов)
    @Query("SELECT COUNT(b) > 0 FROM Booking b WHERE b.clientName = :clientName AND b.tour.id = :tourId AND b.tour.endDate < :currentDate")
    boolean existsByClientNameAndTourIdAndTourEndDateBefore(
            @Param("clientName") String clientName,
            @Param("tourId") Long tourId,
            @Param("currentDate") LocalDate currentDate);

    // Бронирования по email клиента
    List<Booking> findByClientEmail(String clientEmail);
}