package com.example.tourism_service.service;

import com.example.tourism_service.entity.Tour;
import com.example.tourism_service.entity.Wishlist;
import com.example.tourism_service.repository.WishlistRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final TourService tourService;

    public WishlistService(WishlistRepository wishlistRepository, TourService tourService) {
        this.wishlistRepository = wishlistRepository;
        this.tourService = tourService;
    }

    public List<Tour> getWishlist(String clientEmail) {
        return wishlistRepository.findByClientEmail(clientEmail)
                .stream()
                .map(Wishlist::getTour)
                .filter(tour -> tourService.isTourAvailable(tour)) // используй свой метод проверки доступности
                .collect(Collectors.toList());
    }

    @Transactional
    public void addToWishlist(String clientEmail, Long tourId) {
        Tour tour = tourService.getTourById(tourId);

        if (!tourService.isTourAvailable(tour)) {
            throw new RuntimeException("Тур недоступен (нет мест или уже прошёл)");
        }

        if (wishlistRepository.findByClientEmailAndTourId(clientEmail, tourId).isPresent()) {
            throw new RuntimeException("Тур уже в избранном");
        }

        Wishlist wishlist = new Wishlist();
        wishlist.setClientEmail(clientEmail);
        wishlist.setTour(tour);
        wishlistRepository.save(wishlist);
    }

    @Transactional
    public void removeFromWishlist(String clientEmail, Long tourId) {
        wishlistRepository.deleteByClientEmailAndTourId(clientEmail, tourId);
    }
}