package com.example.InsuranceSystem.v11.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Embeddable
public class Car {
    private @Getter @Setter String model;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)

    private @Getter @Setter CarType type;
    private @Getter @Setter int manufacturingYear;
    private @Getter @Setter double carPrice;

    public Car(CarType type, String model, int manufacturingYear, double carPrice) {
        this.model = model;
        this.manufacturingYear = manufacturingYear;
        this.carPrice = carPrice;
        this.type = type;
    }
}
