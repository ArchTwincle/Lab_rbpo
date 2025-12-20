package com.example.tourism_service.controller;

import com.example.tourism_service.entity.Guide;
import com.example.tourism_service.service.GuideService;
import jakarta.validation.Valid;  // ИЗМЕНИЛИ ЗДЕСЬ
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/guides")
public class GuideController {
    private final GuideService guideService;

    public GuideController(GuideService guideService) {
        this.guideService = guideService;
    }

    @GetMapping
    public List<Guide> getAllGuides() {
        return guideService.findAll();
    }

    @GetMapping("/{id}")
    public Guide getGuideById(@PathVariable Long id) {
        return guideService.findById(id);
    }

    @PostMapping
    public ResponseEntity<?> createGuide(@Valid @RequestBody Guide guide) {
        return ResponseEntity.ok(guideService.save(guide));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateGuide(@PathVariable Long id, @Valid @RequestBody Guide guideDetails) {
        return ResponseEntity.ok(guideService.update(id, guideDetails));
    }

    @DeleteMapping("/{id}")
    public void deleteGuide(@PathVariable Long id) {
        guideService.delete(id);
    }
}