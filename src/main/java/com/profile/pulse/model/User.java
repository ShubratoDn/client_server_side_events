package com.profile.pulse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "fakeUsers")
@Data
@NoArgsConstructor
public class FakeUser {
    @Id
    private String id;
    private String name;
    private String address;
    private double latitude;
    private double longitude;
    private String birthData;
    private String phone;
    private String email;
    private String username;
    private int age; // This will be our transformed field
    private int bonus;
    private String company;
    private int height;
    private double weight;
    private String blood;
    private String color;
    private double bmi; // Another transformed field
    private LocalDateTime createdAt;

    public FakeUser(String id, String name, String address, double latitude, double longitude, String birthData, String phone, String email, String username, int age, int bonus, String company, int height, double weight, String blood, String color, double bmi, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.birthData = birthData;
        this.phone = phone;
        this.email = email;
        this.username = username;
        this.age = age;
        this.bonus = bonus;
        this.company = company;
        this.height = height;
        this.weight = weight;
        this.blood = blood;
        this.color = color;
        this.bmi = bmi;
        this.createdAt = createdAt;
    }
}