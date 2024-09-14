package com.example.InsuranceSystem.v11.DTO;

import java.time.LocalDate;

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
    private Integer driverAge;
    private Integer level;
    // Fields specific to ThirdPartyPolicy
    private String comments;
}