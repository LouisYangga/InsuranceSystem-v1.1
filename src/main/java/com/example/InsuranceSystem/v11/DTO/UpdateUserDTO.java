package com.example.InsuranceSystem.v11.DTO;

import com.example.InsuranceSystem.v11.entity.Address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateUserDTO {
    String name;
    Address address;
}
