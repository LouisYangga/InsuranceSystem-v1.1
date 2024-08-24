package com.example.InsuranceSystem.v11.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PasswordDTO {
    String oldPassword;
    String newPassword;
}
