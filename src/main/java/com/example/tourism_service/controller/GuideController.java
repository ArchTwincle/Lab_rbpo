package com.example.tourism_service.controller;

import com.example.tourism_service.entity.Guide;
import com.example.tourism_service.repository.GuideRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/guides")
public class GuideController {

    private final GuideRepository guideRepository;

    public GuideController(GuideRepository guideRepository) {
        this.guideRepository = guideRepository;
    }

    // Добавление нового гида — ДОСТУП ТОЛЬКО ДЛЯ ADMIN
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Guide createGuide(@RequestBody Guide guide) {
        return guideRepository.save(guide);
    }

    // Просмотр всех гидов — также сделаем только для ADMIN
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Guide> getAllGuides() {
        return guideRepository.findAll();
    }
}