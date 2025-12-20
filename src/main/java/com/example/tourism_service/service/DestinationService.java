package com.example.tourism_service.service;

import com.example.tourism_service.entity.Destination;
import com.example.tourism_service.repository.BookingRepository;
import com.example.tourism_service.repository.DestinationRepository;
import com.example.tourism_service.repository.TourRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DestinationService {

    private final DestinationRepository destinationRepository;
    private final TourRepository tourRepository;
    private final BookingRepository bookingRepository;

    public DestinationService(DestinationRepository destinationRepository,
                              TourRepository tourRepository,
                              BookingRepository bookingRepository) {
        this.destinationRepository = destinationRepository;
        this.tourRepository = tourRepository;
        this.bookingRepository = bookingRepository;
    }

    public List<Destination> getAllDestinations() {
        return destinationRepository.findAll();
    }

    public Destination getDestinationById(Long id) {
        return destinationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Направление не найдено"));
    }

    public Destination save(Destination destination) {
        return destinationRepository.save(destination);
    }

    // Популярные направления по количеству бронирований
    public List<Destination> getPopularDestinations(int limit) {
        return destinationRepository.findAll().stream()
                .sorted((d1, d2) -> {
                    long bookings1 = tourRepository.findByDestinationsContaining(d1).stream()
                            .mapToLong(t -> bookingRepository.countByTourId(t.getId()))
                            .sum();
                    long bookings2 = tourRepository.findByDestinationsContaining(d2).stream()
                            .mapToLong(t -> bookingRepository.countByTourId(t.getId()))
                            .sum();
                    return Long.compare(bookings2, bookings1); // по убыванию
                })
                .limit(limit)
                .collect(Collectors.toList());
    }
}