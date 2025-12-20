package com.example.tourism_service.controller;

import com.example.tourism_service.entity.Booking;
import com.example.tourism_service.entity.Tour;
import com.example.tourism_service.repository.BookingRepository;
import com.example.tourism_service.repository.TourRepository;
import com.example.tourism_service.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final BookingRepository bookingRepository;
    private final TourRepository tourRepository; // Добавили для безопасной загрузки тура

    public BookingController(BookingService bookingService,
                             BookingRepository bookingRepository,
                             TourRepository tourRepository) {
        this.bookingService = bookingService;
        this.bookingRepository = bookingRepository;
        this.tourRepository = tourRepository;
    }

    // GET: Получить все бронирования — удобно видеть, какие туры забронированы
    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    // POST: Создать новое бронирование (для ручного тестирования в Postman)
    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        // Валидация основных полей
        if (booking.getClientName() == null || booking.getClientName().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        if (booking.getClientEmail() == null || booking.getClientEmail().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        if (booking.getTour() == null || booking.getTour().getId() == null) {
            return ResponseEntity.badRequest().body(null);
        }

        // Загружаем полный тур из базы (чтобы избежать null в maxParticipants и других полях)
        Tour fullTour = tourRepository.findById(booking.getTour().getId())
                .orElseThrow(() -> new RuntimeException("Тур с указанным ID не найден"));

        // Проверка доступности мест
        long currentBookings = bookingRepository.countByTourId(fullTour.getId());
        if (fullTour.getMaxParticipants() == null || currentBookings >= fullTour.getMaxParticipants()) {
            throw new RuntimeException("Нет свободных мест на этот тур");
        }

        // Устанавливаем дату бронирования и полный объект тура
        booking.setBookingDate(LocalDateTime.now());
        booking.setTour(fullTour);

        Booking savedBooking = bookingRepository.save(booking);
        return ResponseEntity.ok(savedBooking);
    }

    // DELETE: Отмена бронирования с правилами возврата денег
    @DeleteMapping("/{id}/cancel")
    public ResponseEntity<Map<String, Object>> cancelBooking(
            @PathVariable Long id,
            @RequestParam String clientName,
            @RequestParam String clientEmail) {

        Map<String, Object> result = bookingService.cancelBooking(id, clientName, clientEmail);
        return ResponseEntity.ok(result);
    }
}