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

    // public static MyDate convertToMyDateEntity(MyDateDTO myDateDTO) {
    //     if (myDateDTO == null) return null;

    //     MyDate myDate = new MyDate();
    //     myDate.setDay(myDateDTO.getDay());
    //     myDate.setMonth(myDateDTO.getMonth());
    //     myDate.setYear(myDateDTO.getYear());

    //     return myDate;
    // }

    public static CarDTO convertToCarDTO(Car car) {
        if (car == null) return null;

        CarDTO carDTO = new CarDTO();
        carDTO.setManufacturingYear(car.getManufacturingYear());
        carDTO.setCarPrice(car.getCarPrice());
        carDTO.setModel(car.getModel());
        car.setType(carDTO.getType());

        return carDTO;
    }

    // public static MyDateDTO convertToMyDateDTO(MyDate myDate) {
    //     if (myDate == null) return null;

    //     MyDateDTO myDateDTO = new MyDateDTO();
    //     myDateDTO.setDay(myDate.getDay());
    //     myDateDTO.setMonth(myDate.getMonth());
    //     myDateDTO.setYear(myDate.getYear());

    //     return myDateDTO;
    // }
}
