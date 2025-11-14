package com.example.tourism_service.service;

import com.example.tourism_service.entity.Guide;
import com.example.tourism_service.repository.GuideRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GuideService {
    private final GuideRepository guideRepository;

    public GuideService(GuideRepository guideRepository) {
        this.guideRepository = guideRepository;
    }

    public List<Guide> findAll() {
        return guideRepository.findAll();
    }

    public Guide findById(Long id) {
        return guideRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Guide not found with id: " + id));
    }

    public Guide save(Guide guide) {
        return guideRepository.save(guide);
    }

    public Guide update(Long id, Guide guideDetails) {
        Guide guide = findById(id);
        guide.setName(guideDetails.getName());
        guide.setEmail(guideDetails.getEmail());
        guide.setPhoneNumber(guideDetails.getPhoneNumber());
        guide.setBiography(guideDetails.getBiography());
        return guideRepository.save(guide);
    }

    public void delete(Long id) {
        guideRepository.deleteById(id);
    }
}