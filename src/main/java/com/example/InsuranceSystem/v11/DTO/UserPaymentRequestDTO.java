package com.example.InsuranceSystem.v11.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPaymentRequestDTO {
    @NotNull(message = "Username must not be null")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;
    @NotNull(message = "Flat rate must not be null")
    @Min(value = 0, message = "Flat rate must be greater than or equal to 0")
    private Integer flatRate;
    @Pattern(regexp = "Comprehensive|ThirdParty", message="Invalid Policy Type")
    private String policyType;
}