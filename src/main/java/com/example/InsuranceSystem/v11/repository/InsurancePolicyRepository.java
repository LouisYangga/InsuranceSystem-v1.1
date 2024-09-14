package com.example.InsuranceSystem.v11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.InsuranceSystem.v11.entity.CarType;
import com.example.InsuranceSystem.v11.entity.InsurancePolicy;

import java.time.LocalDate;
import java.util.List;

 
@Repository
public interface InsurancePolicyRepository extends JpaRepository<InsurancePolicy, Long> {
    InsurancePolicy findByIdAndUserUserId(Long policyId, Long userId);
    List<InsurancePolicy> findByPolicyHolderUsername(String policyHolderUsername);
    List<InsurancePolicy> findByInsuranceType(String insuranceType);
    void deleteByPolicyHolderUsername(String policyHolderUsername);
    List<InsurancePolicy> findByCarModelIgnoreCase(String model);
    List<InsurancePolicy> findByCarType(CarType type);
    List<InsurancePolicy> findByExpiryDateBefore(LocalDate expiryDate);
    List<InsurancePolicy> findByExpiryDateAfter(LocalDate expiryDate);
    List<InsurancePolicy> findByExpiryDateBetween(LocalDate startDate, LocalDate endDate);
}




