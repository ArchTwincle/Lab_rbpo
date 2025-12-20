package com.example.tourism_service.service;

import com.example.tourism_service.entity.Tour;
import com.example.tourism_service.repository.BookingRepository;
import com.example.tourism_service.repository.ReviewRepository;
import com.example.tourism_service.repository.TourRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TourService {

    private final TourRepository tourRepository;
    private final BookingRepository bookingRepository;
    private final ReviewRepository reviewRepository;

    public TourService(TourRepository tourRepository,
                       BookingRepository bookingRepository,
                       ReviewRepository reviewRepository) {
        this.tourRepository = tourRepository;
        this.bookingRepository = bookingRepository;
        this.reviewRepository = reviewRepository;
    }

    public List<Tour> getAllTours() {
        return tourRepository.findAll();
    }

    public Tour getTourById(Long id) {
        return tourRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Тур не найден"));
    }

    // Проверка доступности тура (будущий + есть места)
    public boolean isTourAvailable(Tour tour) {
        if (tour.getStartDate().isBefore(LocalDate.now())) {
            return false;
        }
        long booked = bookingRepository.countByTourId(tour.getId());
        return booked < tour.getMaxParticipants();
    }

    // Средний рейтинг тура
    public Double getAverageRating(Long tourId) {
        return reviewRepository.findByTourId(tourId).stream()
                .mapToInt(r -> r.getRating())
                .average()
                .orElse(0.0); // Возвращаем 0.0 вместо null
    }

    // Поиск по ключевым словам
    public List<Tour> searchTours(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllTours();
        }
        return tourRepository.searchByKeyword(keyword.trim());
    }

    // Популярные туры
    public List<Tour> getPopularTours(int limit) {
        LocalDate today = LocalDate.now();

        return tourRepository.findAll().stream()
                .filter(t -> t.getStartDate().isAfter(today.minusDays(1)))
                .sorted((t1, t2) -> {
                    long bookings1 = bookingRepository.countByTourId(t1.getId());
                    long bookings2 = bookingRepository.countByTourId(t2.getId());
                    return Long.compare(bookings2, bookings1);
                })
                .limit(limit)
                .collect(Collectors.toList());
    }

    // Рекомендации похожих туров
    public List<Tour> getRecommendations(Long tourId, int limit) {
        Tour current = getTourById(tourId);
        LocalDate today = LocalDate.now();

        return tourRepository.findAll().stream()
                .filter(t -> !t.getId().equals(tourId))
                .filter(t -> t.getStartDate().isAfter(today.minusDays(1)))
                .filter(this::isTourAvailable)
                .filter(t -> hasCommonDestination(t, current) ||
                        sameGuide(t, current) ||
                        similarPrice(t, current))
                .sorted(Comparator.comparingDouble(this::calculateRecommendationScore).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

    private boolean hasCommonDestination(Tour t1, Tour t2) {
        if (t1.getDestinations() == null || t2.getDestinations() == null) return false;
        return t1.getDestinations().stream()
                .anyMatch(d -> t2.getDestinations().contains(d));
    }

    private boolean sameGuide(Tour t1, Tour t2) {
        return t1.getGuide() != null && t2.getGuide() != null &&
                t1.getGuide().getId().equals(t2.getGuide().getId());
    }

    private boolean similarPrice(Tour t1, Tour t2) {
        if (t1.getPrice() == null || t2.getPrice() == null || t2.getPrice() == 0) {
            return false;
        }
        double ratio = t1.getPrice() / t2.getPrice();
        return ratio >= 0.7 && ratio <= 1.3;
    }

    private double calculateRecommendationScore(Tour tour) {
        Double rating = getAverageRating(tour.getId());
        long bookings = bookingRepository.countByTourId(tour.getId());
        // Комбинированный счёт: 70% рейтинг + 30% популярность
        return rating * 0.7 + bookings * 0.3;
    }

    public Tour saveTour(Tour tour) {
        return tourRepository.save(tour);
    }
}