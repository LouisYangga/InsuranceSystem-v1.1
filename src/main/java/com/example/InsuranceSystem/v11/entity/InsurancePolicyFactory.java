package com.example.InsuranceSystem.v11.entity;

public class InsurancePolicyFactory {
    public static InsurancePolicy createPolicy(String type) throws IllegalArgumentException {
        switch (type) {
            case "Comprehensive":
                return new ComprehensivePolicy();
            case "ThirdParty":
                return new ThirdPartyPolicy();
            default:
                throw new IllegalArgumentException("Unknown policy type: " + type);
        }
    }
}
