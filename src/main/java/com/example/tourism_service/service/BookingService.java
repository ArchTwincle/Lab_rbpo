package com.example.tourism_service.service;

import com.example.tourism_service.entity.Booking;
import com.example.tourism_service.entity.Tour;
import com.example.tourism_service.repository.BookingRepository;
import com.example.tourism_service.repository.TourRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final TourRepository tourRepository;

    // Конструктор с инъекцией зависимостей — ОБЯЗАТЕЛЬНО инициализируем все final-поля
    public BookingService(BookingRepository bookingRepository, TourRepository tourRepository) {
        this.bookingRepository = bookingRepository;
        this.tourRepository = tourRepository;
    }

    // Существующие методы (если есть) — например, создание бронирования и т.д.

    // Новый метод: отмена бронирования с правилами возврата
    @Transactional
    public Map<String, Object> cancelBooking(Long bookingId, String clientName, String clientEmail) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Бронирование с ID " + bookingId + " не найдено"));

        if (!booking.getClientName().equals(clientName) || !booking.getClientEmail().equals(clientEmail)) {
            throw new RuntimeException("Вы можете отменить только своё бронирование");
        }

        Tour tour = booking.getTour();
        LocalDate today = LocalDate.now();

        if (tour.getStartDate().isBefore(today)) {
            throw new RuntimeException("Нельзя отменить бронь на уже начавшийся или прошедший тур");
        }

        long daysUntilStart = ChronoUnit.DAYS.between(today, tour.getStartDate());

        double refundPercent;
        String refundMessage;
        if (daysUntilStart > 14) {
            refundPercent = 1.0;
            refundMessage = "Полный возврат";
        } else if (daysUntilStart > 7) {
            refundPercent = 0.7;
            refundMessage = "Возврат 70%";
        } else {
            refundPercent = 0.0;
            refundMessage = "Возврат невозможен (менее 7 дней до начала)";
        }

        bookingRepository.delete(booking);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Бронирование успешно отменено");
        response.put("refund", refundMessage);
        response.put("refundAmount", tour.getPrice() * refundPercent);
        response.put("daysUntilStart", daysUntilStart);

        return response;
    }

    // Здесь могут быть другие методы сервиса (создание брони, список и т.д.)
}