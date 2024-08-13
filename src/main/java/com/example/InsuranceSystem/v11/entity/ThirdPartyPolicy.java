package com.example.InsuranceSystem.v11.entity;

import jakarta.persistence.Entity;
import lombok.*;

@NoArgsConstructor
@ToString
@Entity
public class ThirdPartyPolicy extends InsurancePolicy{  

    protected @Getter @Setter String comments;

    public ThirdPartyPolicy(String type, String policyHolderUsername, int numberofClaims,Car car, MyDate expirDate, String comments) {
        super(type, policyHolderUsername,numberofClaims, car, expirDate);
        this.comments = comments;
    }
    @Override
    public double calculatePrice(int flatRate) {
        return car.getCarPrice() / 100 + numberOfClaims * 200 + flatRate;
    }

}
