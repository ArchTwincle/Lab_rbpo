package com.example.tourism_service.service;

import com.example.tourism_service.entity.Tour;
import com.example.tourism_service.repository.TourRepository;
import com.example.tourism_service.repository.BookingRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TourService {
    private final TourRepository tourRepository;
    private final BookingRepository bookingRepository;

    public TourService(TourRepository tourRepository, BookingRepository bookingRepository) {
        this.tourRepository = tourRepository;
        this.bookingRepository = bookingRepository;
    }

    public List<Tour> findAll() {
        try {
            return tourRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving tours: " + e.getMessage());
        }
    }

    public Tour findById(Long id) {
        return tourRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tour not found with id: " + id));
    }

    public Tour save(Tour tour) {
        try {
            // Базовая валидация
            if (tour.getName() == null || tour.getName().trim().isEmpty()) {
                throw new RuntimeException("Tour name is required");
            }
            if (tour.getStartDate() == null) {
                throw new RuntimeException("Start date is required");
            }
            if (tour.getEndDate() == null) {
                throw new RuntimeException("End date is required");
            }
            if (tour.getStartDate().isAfter(tour.getEndDate())) {
                throw new RuntimeException("Start date cannot be after end date");
            }

            return tourRepository.save(tour);
        } catch (Exception e) {
            throw new RuntimeException("Error saving tour: " + e.getMessage());
        }
    }

    public Tour update(Long id, Tour tourDetails) {
        try {
            Tour tour = findById(id);

            if (tourDetails.getName() != null) {
                tour.setName(tourDetails.getName());
            }
            if (tourDetails.getDescription() != null) {
                tour.setDescription(tourDetails.getDescription());
            }
            if (tourDetails.getStartDate() != null) {
                tour.setStartDate(tourDetails.getStartDate());
            }
            if (tourDetails.getEndDate() != null) {
                tour.setEndDate(tourDetails.getEndDate());
            }
            if (tourDetails.getPrice() != null) {
                tour.setPrice(tourDetails.getPrice());
            }
            if (tourDetails.getMaxParticipants() != null) {
                tour.setMaxParticipants(tourDetails.getMaxParticipants());
            }
            if (tourDetails.getGuide() != null) {
                tour.setGuide(tourDetails.getGuide());
            }
            if (tourDetails.getDestinations() != null) {
                tour.setDestinations(tourDetails.getDestinations());
            }

            return tourRepository.save(tour);
        } catch (Exception e) {
            throw new RuntimeException("Error updating tour: " + e.getMessage());
        }
    }

    public void delete(Long id) {
        try {
            if (!tourRepository.existsById(id)) {
                throw new RuntimeException("Tour not found with id: " + id);
            }
            tourRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting tour: " + e.getMessage());
        }
    }

    public boolean isTourAvailable(Long tourId) {
        try {
            Tour tour = findById(tourId);
            int bookedCount = bookingRepository.countByTourId(tourId);
            return bookedCount < tour.getMaxParticipants();
        } catch (Exception e) {
            throw new RuntimeException("Error checking tour availability: " + e.getMessage());
        }
    }

    public List<Tour> findAvailableTours() {
        try {
            return tourRepository.findAvailableTours();
        } catch (Exception e) {
            throw new RuntimeException("Error finding available tours: " + e.getMessage());
        }
    }
}