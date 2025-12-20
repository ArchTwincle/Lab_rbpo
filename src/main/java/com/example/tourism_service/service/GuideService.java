package com.example.tourism_service.service;

import com.example.tourism_service.entity.Guide;
import com.example.tourism_service.repository.GuideRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GuideService {

    private final GuideRepository guideRepository;

    public GuideService(GuideRepository guideRepository) {
        this.guideRepository = guideRepository;
    }

    // Получить всех гидов
    public List<Guide> getAllGuides() {
        return guideRepository.findAll();
    }

    // Найти гида по ID
    public Guide getGuideById(Long id) {
        return guideRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Гид с ID " + id + " не найден"));
    }

    // Создать нового гида
    @Transactional
    public Guide createGuide(Guide guide) {
        return guideRepository.save(guide);
    }

    // Обновить данные гида (Исправленная версия без ошибки getName)
    @Transactional
    public Guide updateGuide(Long id, Guide guideDetails) {
        Guide guide = getGuideById(id);

        // Обновляем поля, используя те имена, которые в Guide.java
        guide.setFirstName(guideDetails.getFirstName());
        guide.setLastName(guideDetails.getLastName());
        guide.setSpecialization(guideDetails.getSpecialization());
        guide.setExperienceYears(guideDetails.getExperienceYears());

        return guideRepository.save(guide);
    }

    // Удалить гида
    @Transactional
    public void deleteGuide(Long id) {
        Guide guide = getGuideById(id);
        guideRepository.delete(guide);
    }
}