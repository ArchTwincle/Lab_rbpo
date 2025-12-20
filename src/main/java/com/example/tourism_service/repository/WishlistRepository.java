package com.example.tourism_service.repository;

import com.example.tourism_service.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    List<Wishlist> findByClientEmail(String clientEmail);

    Optional<Wishlist> findByClientEmailAndTourId(String clientEmail, Long tourId);

    void deleteByClientEmailAndTourId(String clientEmail, Long tourId);
}