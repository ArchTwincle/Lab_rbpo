package com.example.tourism_service.controller;

import com.example.tourism_service.entity.Guide;
import com.example.tourism_service.service.GuideService;
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
    public Guide createGuide(@RequestBody Guide guide) {
        return guideService.save(guide);
    }

    @PutMapping("/{id}")
    public Guide updateGuide(@PathVariable Long id, @RequestBody Guide guideDetails) {
        return guideService.update(id, guideDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteGuide(@PathVariable Long id) {
        guideService.delete(id);
    }
}