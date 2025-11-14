package com.example.tourism_service.service;

import com.example.tourism_service.entity.Destination;
import com.example.tourism_service.repository.DestinationRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DestinationService {
    private final DestinationRepository destinationRepository;

    public DestinationService(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    public List<Destination> findAll() {
        return destinationRepository.findAll();
    }

    public Destination findById(Long id) {
        return destinationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Destination not found with id: " + id));
    }

    public Destination save(Destination destination) {
        return destinationRepository.save(destination);
    }

    public Destination update(Long id, Destination destinationDetails) {
        Destination destination = findById(id);
        destination.setName(destinationDetails.getName());
        destination.setCountry(destinationDetails.getCountry());
        destination.setDescription(destinationDetails.getDescription());
        return destinationRepository.save(destination);
    }

    public void delete(Long id) {
        destinationRepository.deleteById(id);
    }
}
