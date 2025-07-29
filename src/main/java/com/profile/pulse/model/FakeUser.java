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
@AllArgsConstructor
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
    private double bmi; // Another transformed field
    private LocalDateTime createdAt;
}