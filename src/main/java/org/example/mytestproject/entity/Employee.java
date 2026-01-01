package org.example.mytestproject.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


@Entity
@Data
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "department") // УБРАЛ nullable = false
    private String department;

    @Column(name = "post")
    private String post;

    // Добавьте поля из DTO если нужно
    @Column(name = "personal_code", unique = true)
    private String personalCode;

    @Column(name = "qr_code_data", columnDefinition = "TEXT")
    private String qrCodeData;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        updatedAt = LocalDateTime.now();

        // Установите дефолтное значение для department
        if (department == null || department.isEmpty()) {
            department = "Не указан";
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public String getFullName(){
        return firstName + " " + lastName;
    }
}
