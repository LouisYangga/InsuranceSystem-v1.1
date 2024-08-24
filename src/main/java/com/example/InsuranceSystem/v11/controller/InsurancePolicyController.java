package com.example.InsuranceSystem.v11.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.InsuranceSystem.v11.DTO.DynamicDTO;
import com.example.InsuranceSystem.v11.DTO.InsurancePolicyDTO;
import com.example.InsuranceSystem.v11.entity.InsurancePolicy;
import com.example.InsuranceSystem.v11.entity.User;
import com.example.InsuranceSystem.v11.exception.InsuranceExceptions;
import com.example.InsuranceSystem.v11.service.InsurancePolicyService;
import com.example.InsuranceSystem.v11.service.UserService;

@RestController
@RequestMapping("/policies")
public class InsurancePolicyController {
    
    private final UserService userService;
    private final InsurancePolicyService insurancePolicyService;
    public InsurancePolicyController(UserService userService, InsurancePolicyService insurancePolicyService){
        this.userService = userService;
        this.insurancePolicyService = insurancePolicyService;
    }

    @GetMapping
    public ResponseEntity<List<InsurancePolicy>> getAllPolicies(){
        List<InsurancePolicy> policies = insurancePolicyService.getAllInsurancePolicies();
        return ResponseEntity.ok(policies);
    }

    @GetMapping("/{policyId}")
    public ResponseEntity<InsurancePolicy> getPolicyById(@PathVariable Long policyId){
        InsurancePolicy policy = insurancePolicyService.getInsurancePolicyById(policyId);
        return ResponseEntity.ok(policy);
    }
    @DeleteMapping("/{policyId}")
    public ResponseEntity<Void> deletePolicy(@PathVariable Long policyId) { 
        insurancePolicyService.deleteById(policyId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/username")
    public ResponseEntity<List<InsurancePolicy>> getPolicyByUsername(@RequestParam(name="username", defaultValue = "None") String username){
        List<InsurancePolicy> policies = insurancePolicyService.getPoliciesByUsername(username);
        User user = userService.findByUsername(username);
        if(user==null){
            throw new InsuranceExceptions.UserNotFoundException("User not found for user: " + username);
        }
        if(policies.isEmpty()){
            throw new InsuranceExceptions.NoPoliciesException("No Policies Found for User: " + user.getUsername());
        }
        return ResponseEntity.ok(policies);
    }
    @GetMapping("/price")
    public ResponseEntity<DynamicDTO> calcSinglePayment(@RequestParam(name="id", defaultValue = "0")Long policyId, @RequestParam(name="rate", defaultValue = "1") int flatRate) {
        InsurancePolicy policy = insurancePolicyService.getInsurancePolicyById(policyId);
        if(policy==null){
            throw new InsuranceExceptions.NoPoliciesException("Policiy with ID: " + policyId +" Not Found");
        }
        double cost = insurancePolicyService.calcSinglePayment(policyId, flatRate);
        DynamicDTO data = new DynamicDTO();
        data.setField("policyType", policy.getInsuranceType());
        data.setField("policyHolderUsername", policy.getPolicyHolderUsername());
        data.setField("policyId", policyId);
        data.setField("paymentCost", cost);
        return ResponseEntity.ok(data);
    }
    
    @PostMapping
    public ResponseEntity<String> addPolicy(@RequestBody InsurancePolicyDTO insurancePolicyDTO){
        try {
            String policyType = insurancePolicyDTO.getInsuranceType();
            InsurancePolicy insurancePolicy = insurancePolicyService.createInsurancePolicy(insurancePolicyDTO);
            // Set the user
            User user = userService.findByUsername(insurancePolicy.getPolicyHolderUsername());
            if (user == null) {
                throw new InsuranceExceptions.UserNotFoundException("Username not Found");
            }
            insurancePolicy.setUser(user);
            // Add the policy
            boolean success = userService.addPolicy(insurancePolicy, insurancePolicy.getUser().getUserId());
            if (success) {
                return ResponseEntity.ok(policyType + " Policy added successfully");
            } else {
                return ResponseEntity.status(500).body("Failed to add policy");
            }
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

}
