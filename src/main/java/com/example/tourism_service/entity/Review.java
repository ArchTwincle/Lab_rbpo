package com.example.tourism_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Имя автора обязательно")
    @Size(min = 2, max = 100, message = "Имя должно быть от 2 до 100 символов")
    @Column(nullable = false)
    private String authorName;

    @NotBlank(message = "Текст отзыва обязателен")
    @Size(min = 10, max = 2000, message = "Отзыв должен быть от 10 до 2000 символов")
    @Column(length = 2000, nullable = false)
    private String content;

    @NotNull(message = "Рейтинг обязателен")
    @Min(value = 1, message = "Минимальный рейтинг - 1")
    @Max(value = 5, message = "Максимальный рейтинг - 5")
    @Column(nullable = false)
    private Integer rating;

    @PastOrPresent(message = "Дата отзыва не может быть в будущем")
    @Column(nullable = false)
    private LocalDate reviewDate;

    @NotNull(message = "Тур обязателен")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_id", nullable = false)
    private Tour tour;

    public Review() {}

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }

    public LocalDate getReviewDate() { return reviewDate; }
    public void setReviewDate(LocalDate reviewDate) { this.reviewDate = reviewDate; }

    public Tour getTour() { return tour; }
    public void setTour(Tour tour) { this.tour = tour; }
}