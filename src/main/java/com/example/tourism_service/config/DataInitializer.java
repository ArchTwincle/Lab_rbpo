package com.example.tourism_service.config;

import com.example.tourism_service.entity.Destination;
import com.example.tourism_service.entity.Guide;
import com.example.tourism_service.repository.DestinationRepository;
import com.example.tourism_service.repository.GuideRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final GuideRepository guideRepository;
    private final DestinationRepository destinationRepository;

    public DataInitializer(GuideRepository guideRepository,
                           DestinationRepository destinationRepository) {
        this.guideRepository = guideRepository;
        this.destinationRepository = destinationRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Создаём данные только если хотя бы одна из таблиц пуста
        if (guideRepository.count() == 0 || destinationRepository.count() == 0) {
            System.out.println("База данных пуста или частично заполнена. Создаём базовые данные (гиды и направления)...");

            // === Создание гидов ===
            if (guideRepository.count() == 0) {
                Guide guide1 = new Guide();
                guide1.setName("Анна Иванова");
                guide1.setEmail("anna@example.com");
                guide1.setPhoneNumber("+79161234567");
                guide1.setBiography("Опытный гид по Европе, владеет 4 языками");
                guideRepository.save(guide1);

                Guide guide2 = new Guide();
                guide2.setName("Михаил Петров");
                guide2.setEmail("mikhail@example.com");
                guide2.setPhoneNumber("+79259876543");
                guide2.setBiography("Специалист по Азии и экотуризму");
                guideRepository.save(guide2);

                System.out.println("Создано 2 гида (ID: 1 и 2).");
            }

            // === Создание направлений ===
            if (destinationRepository.count() == 0) {
                createDestination("Париж", "Франция", "Город любви, Эйфелева башня, Лувр");
                createDestination("Рим", "Италия", "Вечный город, Колизей, Ватикан");
                createDestination("Токио", "Япония", "Смесь традиций и современных технологий");
                createDestination("Бали", "Индонезия", "Райский остров с пляжами и вулканами");

            }
        } else {
            System.out.println("Базовые данные (гиды и направления) уже существуют. Инициализация пропущена.");
        }
    }

    private void createDestination(String name, String country, String description) {
        Destination destination = new Destination();
        destination.setName(name);
        destination.setCountry(country);
        destination.setDescription(description);
        destinationRepository.save(destination);
    }
}