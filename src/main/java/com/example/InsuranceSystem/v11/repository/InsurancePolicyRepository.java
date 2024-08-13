package com.example.InsuranceSystem.v11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.InsuranceSystem.v11.entity.InsurancePolicy;
import java.util.List;

 
@Repository
public interface InsurancePolicyRepository extends JpaRepository<InsurancePolicy, Long> {
    InsurancePolicy findByIdAndUserUserId(Long policyId, Long userId);
    List<InsurancePolicy> findByPolicyHolderUsername(String policyHolderUsername);
    List<InsurancePolicy> findByInsuranceType(String insuranceType);
}




