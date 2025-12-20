package com.example.tourism_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "guides")
public class Guide {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Имя гида обязательно")
    @Size(min = 2, max = 100, message = "Имя должно быть от 2 до 100 символов")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Email обязателен")
    @Email(message = "Неверный формат email")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "Номер телефона обязателен")
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Неверный формат номера телефона")
    @Column(nullable = false)
    private String phoneNumber;

    @Size(max = 1000, message = "Биография не должна превышать 1000 символов")
    @Column(length = 1000)
    private String biography;

    @OneToMany(mappedBy = "guide", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Tour> tours;

    public Guide() {}

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getBiography() { return biography; }
    public void setBiography(String biography) { this.biography = biography; }

    public List<Tour> getTours() { return tours; }
    public void setTours(List<Tour> tours) { this.tours = tours; }
}