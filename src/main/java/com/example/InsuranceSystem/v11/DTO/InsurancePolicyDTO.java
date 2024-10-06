package com.example.InsuranceSystem.v11.DTO;

import java.time.LocalDate;

import com.example.InsuranceSystem.v11.validator.DateRange;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InsurancePolicyDTO {
    private String insuranceType;
    private String policyHolderUsername;
    private Integer numberOfClaims;
    private CarDTO car;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @NotNull(message = "Expiry date cannot be empty")
    @Future(message = "Expiry date should in the future")
    private LocalDate expiryDate;
    // Fields specific to ComprehensivePolicy
    // @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    // @NotNull(message = "dob cannot be empty")
    // @DateRange(min = 18, max=100, message = "Age must be between 18 and 100")
    private LocalDate dob;
    private Integer level;
    // Fields specific to ThirdPartyPolicy
    private String comments;
}