package com.example.InsuranceSystem.v11.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.example.InsuranceSystem.v11.entity.CarType; // Import the shared enum
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarDTO {
    private int manufacturingYear;
    private double carPrice;
    private String model;
    private CarType type;
}
