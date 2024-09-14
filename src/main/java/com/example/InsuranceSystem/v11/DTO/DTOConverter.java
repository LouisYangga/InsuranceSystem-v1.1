package com.example.InsuranceSystem.v11.DTO;

import com.example.InsuranceSystem.v11.entity.Car;
import com.example.InsuranceSystem.v11.entity.CarType;
import com.example.InsuranceSystem.v11.exception.InsuranceExceptions;

public class DTOConverter {

    public static Car convertToCarEntity(CarDTO carDTO) throws InsuranceExceptions.InvalidCarTypeException{
        if (carDTO == null) return null;

        Car car = new Car();
        car.setManufacturingYear(carDTO.getManufacturingYear());
        car.setCarPrice(carDTO.getCarPrice());
        car.setModel(carDTO.getModel());
        car.setType(CarType.validate(carDTO.getType().name()));
        return car;
    }

    public static CarDTO convertToCarDTO(Car car) {
        if (car == null) return null;

        CarDTO carDTO = new CarDTO();
        carDTO.setManufacturingYear(car.getManufacturingYear());
        carDTO.setCarPrice(car.getCarPrice());
        carDTO.setModel(car.getModel());
        car.setType(carDTO.getType());

        return carDTO;
    }

}
