package com.example.InsuranceSystem.v11.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
public class MyDate {

    @Column(name = "expiry_day")
    private @Getter @Setter int day;

    @Column(name = "expiry_month")
    private @Getter @Setter int month;

    @Column(name = "expiry_year")
    private @Getter @Setter int year;
}
