package com.example.tourism_service.config;

import com.example.tourism_service.entity.*;
import com.example.tourism_service.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    private final GuideRepository guideRepository;
    private final DestinationRepository destinationRepository;
    private final TourRepository tourRepository;
    private final BookingRepository bookingRepository;
    private final ReviewRepository reviewRepository;

    public DataInitializer(GuideRepository guideRepository,
                           DestinationRepository destinationRepository,
                           TourRepository tourRepository,
                           BookingRepository bookingRepository,
                           ReviewRepository reviewRepository) {
        this.guideRepository = guideRepository;
        this.destinationRepository = destinationRepository;
        this.tourRepository = tourRepository;
        this.bookingRepository = bookingRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Очищаем базу (опционально)
        reviewRepository.deleteAll();
        bookingRepository.deleteAll();
        tourRepository.deleteAll();
        destinationRepository.deleteAll();
        guideRepository.deleteAll();

        // Создаем гидов
        Guide guide1 = new Guide();
        guide1.setName("Анна Иванова");
        guide1.setEmail("anna@example.com");
        guide1.setPhoneNumber("+79161111111");
        guide1.setBiography("Опытный гид с 10-летним стажем работы в Европе");
        guide1 = guideRepository.save(guide1);

        Guide guide2 = new Guide();
        guide2.setName("Дмитрий Петров");
        guide2.setEmail("dmitry@example.com");
        guide2.setPhoneNumber("+79162222222");
        guide2.setBiography("Специалист по горным походам и экстремальному туризму");
        guide2 = guideRepository.save(guide2);

        // Создаем направления
        Destination destination1 = new Destination();
        destination1.setName("Париж");
        destination1.setCountry("Франция");
        destination1.setDescription("Город любви, искусства и моды");
        destination1 = destinationRepository.save(destination1);

        Destination destination2 = new Destination();
        destination2.setName("Альпы");
        destination2.setCountry("Швейцария");
        destination2.setDescription("Живописные горные пейзажи для любителей активного отдыха");
        destination2 = destinationRepository.save(destination2);

        Destination destination3 = new Destination();
        destination3.setName("Прага");
        destination3.setCountry("Чехия");
        destination3.setDescription("Город ста башен с богатой историей и архитектурой");
        destination3 = destinationRepository.save(destination3);

        // Создаем туры
        Tour tour1 = new Tour();
        tour1.setName("Романтический Париж");
        tour1.setDescription("5 незабываемых дней в столице Франции");
        tour1.setStartDate(LocalDate.of(2024, 8, 1));
        tour1.setEndDate(LocalDate.of(2024, 8, 6));
        tour1.setPrice(1200.0);
        tour1.setMaxParticipants(8);
        tour1.setGuide(guide1);
        tour1.setDestinations(Arrays.asList(destination1));
        tour1 = tourRepository.save(tour1);

        Tour tour2 = new Tour();
        tour2.setName("Альпийское приключение");
        tour2.setDescription("7 дней в горах Швейцарии для любителей природы");
        tour2.setStartDate(LocalDate.of(2024, 9, 1));
        tour2.setEndDate(LocalDate.of(2024, 9, 8));
        tour2.setPrice(1800.0);
        tour2.setMaxParticipants(6);
        tour2.setGuide(guide2);
        tour2.setDestinations(Arrays.asList(destination2));
        tour2 = tourRepository.save(tour2);

        Tour tour3 = new Tour();
        tour3.setName("Магия Праги");
        tour3.setDescription("4-дневное путешествие по старинным улочкам Чехии");
        tour3.setStartDate(LocalDate.of(2024, 10, 1));
        tour3.setEndDate(LocalDate.of(2024, 10, 5));
        tour3.setPrice(950.0);
        tour3.setMaxParticipants(12);
        tour3.setGuide(guide1);
        tour3.setDestinations(Arrays.asList(destination3));
        tour3 = tourRepository.save(tour3);

        // Создаем бронирования
        Booking booking1 = new Booking();
        booking1.setClientName("Иван Сидоров");
        booking1.setClientEmail("ivan@example.com");
        booking1.setBookingDate(LocalDateTime.now().minusDays(5));
        booking1.setTour(tour1);
        booking1 = bookingRepository.save(booking1);

        Booking booking2 = new Booking();
        booking2.setClientName("Мария Козлова");
        booking2.setClientEmail("maria@example.com");
        booking2.setBookingDate(LocalDateTime.now().minusDays(3));
        booking2.setTour(tour1);
        booking2 = bookingRepository.save(booking2);

        Booking booking3 = new Booking();
        booking3.setClientName("Алексей Новиков");
        booking3.setClientEmail("alexey@example.com");
        booking3.setBookingDate(LocalDateTime.now().minusDays(1));
        booking3.setTour(tour2);
        booking3 = bookingRepository.save(booking3);

        // Создаем отзывы (для завершенных туров)
        Review review1 = new Review();
        review1.setAuthorName("Иван Сидоров");
        review1.setContent("Прекрасный тур! Гид Анна - настоящий профессионал. Все было организовано на высшем уровне.");
        review1.setRating(5);
        review1.setReviewDate(LocalDate.now().minusDays(2));
        review1.setTour(tour1);
        review1 = reviewRepository.save(review1);

        Review review2 = new Review();
        review2.setAuthorName("Мария Козлова");
        review2.setContent("Очень понравилось! Париж прекрасен, гид внимательна и знающа. Рекомендую!");
        review2.setRating(4);
        review2.setReviewDate(LocalDate.now().minusDays(1));
        review2.setTour(tour1);
        review2 = reviewRepository.save(review2);

        System.out.println("==========================================");
        System.out.println("ТЕСТОВЫЕ ДАННЫЕ УСПЕШНО СОЗДАНЫ!");
        System.out.println("Создано:");
        System.out.println("- Гидов: " + guideRepository.count());
        System.out.println("- Направлений: " + destinationRepository.count());
        System.out.println("- Туров: " + tourRepository.count());
        System.out.println("- Бронирований: " + bookingRepository.count());
        System.out.println("- Отзывов: " + reviewRepository.count());
        System.out.println("==========================================");
    }
}