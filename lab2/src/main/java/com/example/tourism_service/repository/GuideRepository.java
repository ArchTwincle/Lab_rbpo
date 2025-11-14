package com.example.tourism_service.repository;

import com.example.tourism_service.entity.Guide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuideRepository extends JpaRepository<Guide, Long> {
    Guide findByEmail(String email);
}