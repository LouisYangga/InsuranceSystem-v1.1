package com.example.InsuranceSystem.v11.entity;

import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class ComprehensivePolicy extends InsurancePolicy{

    protected int driverAge;
    protected int level;
    public ComprehensivePolicy(String type, String policyHolderUsername, int numberofClaims,Car car, MyDate expiryDate, int driverAge, int level) {
        super(type,policyHolderUsername, numberofClaims, car, expiryDate);
        this.driverAge = driverAge;
        this.level = level; 
    }
    @Override
    public double calculatePrice(int flatRate){
        if (driverAge <= 30) {
            return car.getCarPrice() / 50 + numberOfClaims * 200 + flatRate + (30 - driverAge) * 50;
        } else {
            return car.getCarPrice() / 50 + numberOfClaims * 200 + flatRate;
        }
    }
}
