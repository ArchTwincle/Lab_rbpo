package com.example.tourism_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Позволяет использовать аннотацию @PreAuthorize над методами
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. Отключаем CSRF для тестирования через Postman (иначе POST запросы будут блокироваться)
                .csrf(csrf -> csrf.disable())

                // 2. Настройка прав доступа
                .authorizeHttpRequests(auth -> auth

                        // РЕГИСТРАЦИЯ: Только для Админа
                        .requestMatchers("/api/auth/register").hasRole("ADMIN")

                        // ГИДЫ: Просмотр и создание только для Админа
                        .requestMatchers("/api/guides/**").hasRole("ADMIN")

                        // ТУРЫ: Просматривать могут все, создавать — Админ или Гид
                        .requestMatchers(HttpMethod.GET, "/api/tours/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/tours/**").hasAnyRole("ADMIN", "GUIDE")
                        .requestMatchers(HttpMethod.DELETE, "/api/tours/**").hasRole("ADMIN")

                        // ОСТАЛЬНОЕ: Любой эндпоинт требует хотя бы авторизации (USER, GUIDE или ADMIN)
                        .anyRequest().authenticated()
                )

                // 3. Используем базовую аутентификацию (окно ввода логина/пароля в браузере или вкладка Auth в Postman)
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Используем BCrypt для надежного хеширования паролей в базе данных
        return new BCryptPasswordEncoder();
    }
}