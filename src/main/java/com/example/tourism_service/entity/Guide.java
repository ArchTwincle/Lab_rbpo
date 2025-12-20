package com.example.tourism_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Сущность "Гид".
 * Содержит информацию о сотрудниках, которые проводят туры.
 */
@Entity
@Table(name = "guides")
@Data // Автоматически генерирует геттеры, сеттеры и другие методы
@NoArgsConstructor // Пустой конструктор для Hibernate
@AllArgsConstructor // Конструктор со всеми полями
public class Guide {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "specialization")
    private String specialization; // Специализация (например, "Горный гид", "Историк")

    @Column(name = "experience_years")
    private Integer experienceYears; // Стаж работы в годах

    /**
     * Вспомогательный метод для получения полного имени.
     * Полезно при выводе в консоль или в интерфейсе.
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }
}