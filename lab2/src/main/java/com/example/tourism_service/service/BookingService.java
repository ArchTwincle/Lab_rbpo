package com.example.tourism_service.service;

import com.example.tourism_service.entity.Booking;
import com.example.tourism_service.repository.BookingRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final TourService tourService;

    public BookingService(BookingRepository bookingRepository, TourService tourService) {
        this.bookingRepository = bookingRepository;
        this.tourService = tourService;
    }

    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    public Booking findById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));
    }

    public Booking save(Booking booking) {
        // Проверяем, что тур указан
        if (booking.getTour() == null || booking.getTour().getId() == null) {
            throw new RuntimeException("Tour is required for booking");
        }

        // Проверяем доступность тура
        if (!tourService.isTourAvailable(booking.getTour().getId())) {
            throw new RuntimeException("No available spots for this tour");
        }

        booking.setBookingDate(LocalDateTime.now());
        return bookingRepository.save(booking);
    }

    public Booking update(Long id, Booking bookingDetails) {
        Booking booking = findById(id);
        booking.setClientName(bookingDetails.getClientName());
        booking.setClientEmail(bookingDetails.getClientEmail());

        // Проверяем, что тур указан при обновлении
        if (bookingDetails.getTour() != null && bookingDetails.getTour().getId() != null) {
            booking.setTour(bookingDetails.getTour());
        }

        return bookingRepository.save(booking);
    }

    public void delete(Long id) {
        bookingRepository.deleteById(id);
    }

    public List<Booking> findByTourId(Long tourId) {
        return bookingRepository.findByTourId(tourId);
    }

    public List<Booking> findByClientEmail(String clientEmail) {
        return bookingRepository.findByClientEmail(clientEmail);
    }
}