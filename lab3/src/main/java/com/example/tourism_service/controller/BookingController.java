package com.example.tourism_service.controller;

import com.example.tourism_service.entity.Booking;
import com.example.tourism_service.service.BookingService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingService.findAll();
    }

    @GetMapping("/{id}")
    public Booking getBookingById(@PathVariable Long id) {
        return bookingService.findById(id);
    }

    @PostMapping
    public Booking createBooking(@RequestBody Booking booking) {
        return bookingService.save(booking);
    }

    @PutMapping("/{id}")
    public Booking updateBooking(@PathVariable Long id, @RequestBody Booking bookingDetails) {
        return bookingService.update(id, bookingDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteBooking(@PathVariable Long id) {
        bookingService.delete(id);
    }

    @GetMapping("/tour/{tourId}")
    public List<Booking> getBookingsByTour(@PathVariable Long tourId) {
        return bookingService.findByTourId(tourId);
    }

    @GetMapping("/client/{email}")
    public List<Booking> getBookingsByClientEmail(@PathVariable String email) {
        return bookingService.findByClientEmail(email);
    }
}