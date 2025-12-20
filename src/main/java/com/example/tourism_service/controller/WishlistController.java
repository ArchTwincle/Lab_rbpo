package com.example.tourism_service.controller;

import com.example.tourism_service.entity.Tour;
import com.example.tourism_service.service.WishlistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @GetMapping
    public List<Tour> getWishlist(@RequestParam String clientEmail) {
        return wishlistService.getWishlist(clientEmail);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addToWishlist(
            @RequestParam String clientEmail,
            @RequestParam Long tourId) {
        wishlistService.addToWishlist(clientEmail, tourId);
        return ResponseEntity.ok("Тур добавлен в избранное");
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> removeFromWishlist(
            @RequestParam String clientEmail,
            @RequestParam Long tourId) {
        wishlistService.removeFromWishlist(clientEmail, tourId);
        return ResponseEntity.ok("Тур удалён из избранного");
    }
}