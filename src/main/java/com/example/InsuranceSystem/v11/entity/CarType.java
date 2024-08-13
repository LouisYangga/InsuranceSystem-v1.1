package com.example.InsuranceSystem.v11.entity;

import com.example.InsuranceSystem.v11.exception.InsuranceExceptions;

public enum CarType {
    SUV, SED, LUX, HATCH, TRUCK;

    public static CarType validate(String carTypeStr) throws InsuranceExceptions.InvalidCarTypeException{
        try {
            return CarType.valueOf(carTypeStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InsuranceExceptions.InvalidCarTypeException("Invalid car type: " + carTypeStr);
        }
    }
}