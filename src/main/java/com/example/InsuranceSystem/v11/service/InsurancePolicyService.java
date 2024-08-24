package com.example.InsuranceSystem.v11.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.InsuranceSystem.v11.DTO.DTOConverter;
import com.example.InsuranceSystem.v11.DTO.InsurancePolicyDTO;
import com.example.InsuranceSystem.v11.entity.Car;
import com.example.InsuranceSystem.v11.entity.ComprehensivePolicy;
import com.example.InsuranceSystem.v11.entity.InsurancePolicy;
import com.example.InsuranceSystem.v11.entity.InsurancePolicyFactory;
import com.example.InsuranceSystem.v11.entity.MyDate;
import com.example.InsuranceSystem.v11.entity.ThirdPartyPolicy;
import com.example.InsuranceSystem.v11.exception.InsuranceExceptions;
import com.example.InsuranceSystem.v11.repository.InsurancePolicyRepository;

@Service
public class InsurancePolicyService { 
    private final InsurancePolicyRepository insurancePolicyRepository;
    public InsurancePolicyService(InsurancePolicyRepository insurancePolicyRepository){
        this.insurancePolicyRepository = insurancePolicyRepository;
    }

    public List<InsurancePolicy> getAllInsurancePolicies() {
        return insurancePolicyRepository.findAll();
    }
    public InsurancePolicy getInsurancePolicyById(Long id) {
        return insurancePolicyRepository.findById(id).orElse(null);
    }
    public List<InsurancePolicy> getInsurancePolicyByType(String insuranceBytype){
        return insurancePolicyRepository.findByInsuranceType(insuranceBytype);
    }
    public void deleteInsurancePolicyByUsername(String username){
        insurancePolicyRepository.deleteByPolicyHolderUsername(username);
    }
    public void deleteById(Long id){
        insurancePolicyRepository.deleteById(id);
    }
    public InsurancePolicy saveInsurancePolicy(InsurancePolicy insurancePolicy) {
        return insurancePolicyRepository.save(insurancePolicy);
    }
    public static double calcTotalPayment(List<InsurancePolicy> policies, int flatRate){
        double total = 0;
        for(InsurancePolicy ins:policies){
            total+= ins.calculatePrice(flatRate);
        }
        return total;
    }
    public double calcSinglePayment(Long policyId, int flatRate){
        InsurancePolicy policy = getInsurancePolicyById(policyId);
        return policy.calculatePrice(flatRate);
    }
    public List<InsurancePolicy> getPoliciesByUsername(String username){
        List<InsurancePolicy> policies = insurancePolicyRepository.findByPolicyHolderUsername(username);
        return policies;
    }
    public InsurancePolicy createInsurancePolicy(InsurancePolicyDTO insurancePolicyDTO) throws InsuranceExceptions.InvalidCarTypeException{
            String policyType = (String) insurancePolicyDTO.getInsuranceType();

            // Create the policy using the factory
            InsurancePolicy insurancePolicy = InsurancePolicyFactory.createPolicy(policyType);

            // Set common properties
            insurancePolicy.setPolicyHolderUsername((String) insurancePolicyDTO.getPolicyHolderUsername());
            insurancePolicy.setInsuranceType(policyType);
            insurancePolicy.setNumberOfClaims((Integer) insurancePolicyDTO.getNumberOfClaims());
            //Convert CarDTO to Car entity
            Car car =  DTOConverter.convertToCarEntity(insurancePolicyDTO.getCar());
            MyDate date = DTOConverter.convertToMyDateEntity(insurancePolicyDTO.getExpiryDate());
            insurancePolicy.setCar(car);
            insurancePolicy.setExpiryDate(date);
            // Set type-specific properties
            if (insurancePolicy instanceof ComprehensivePolicy) {
                ComprehensivePolicy comprehensivePolicy = (ComprehensivePolicy) insurancePolicy;
                comprehensivePolicy.setDriverAge(insurancePolicyDTO.getDriverAge());
                comprehensivePolicy.setLevel(insurancePolicyDTO.getLevel());
            } else if (insurancePolicy instanceof ThirdPartyPolicy) {
                ThirdPartyPolicy thirdPartyPolicy = (ThirdPartyPolicy) insurancePolicy;
                // Set properties specific to ThirdPartyPolicy
                thirdPartyPolicy.setComments(insurancePolicyDTO.getComments());
            }
            return insurancePolicy;             
    }
}
