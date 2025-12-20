package com.example.tourism_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(
        name = "destinations",
        uniqueConstraints = @UniqueConstraint(columnNames = {"name", "country"})
)
public class Destination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Название направления обязательно")
    @Size(min = 2, max = 100, message = "Название должно быть от 2 до 100 символов")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Страна обязательна")
    @Size(min = 2, max = 50, message = "Название страны должно быть от 2 до 50 символов")
    @Column(nullable = false)
    private String country;

    @Size(max = 2000, message = "Описание не должно превышать 2000 символов")
    @Column(length = 2000)
    private String description;

    @ManyToMany(mappedBy = "destinations", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Tour> tours;

    public Destination() {}

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<Tour> getTours() { return tours; }
    public void setTours(List<Tour> tours) { this.tours = tours; }
}