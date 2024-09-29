package com.example.InsuranceSystem.v11.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.InsuranceSystem.v11.ApiResponse;
import com.example.InsuranceSystem.v11.DTO.DynamicDTO;
import com.example.InsuranceSystem.v11.DTO.InsurancePolicyDTO;
import com.example.InsuranceSystem.v11.entity.InsurancePolicy;
import com.example.InsuranceSystem.v11.entity.User;
import com.example.InsuranceSystem.v11.exception.InsuranceExceptions;
import com.example.InsuranceSystem.v11.service.InsurancePolicyService;
import com.example.InsuranceSystem.v11.service.UserService;

import jakarta.validation.Valid;

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
    @GetMapping("/model")
    public ResponseEntity<List<InsurancePolicy>> filterByCarModel(@RequestParam(name="model", defaultValue="") String carModel) {
        List<InsurancePolicy> policies = insurancePolicyService.filterByCarsModel(carModel);
        return ResponseEntity.ok(policies);
    }
    @GetMapping("/carType")
    public ResponseEntity<List<InsurancePolicy>> filterByCarType(@RequestParam(name="type", defaultValue = "") String type) {
        List<InsurancePolicy> policies = insurancePolicyService.filterByCarsType(type);
        return ResponseEntity.ok(policies); 
    }
    @GetMapping("/before")
    public ResponseEntity<List<InsurancePolicy>> filterByExpiryDateBefore(@RequestParam(name="date", defaultValue = "") String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.parse(dateString, formatter);
        List<InsurancePolicy> policies = insurancePolicyService.filterByExpiryDateBefore(date);
        return ResponseEntity.ok(policies);
    }
    @GetMapping("/after")
    public ResponseEntity<List<InsurancePolicy>> filterByExpiryDateAfer(@RequestParam(name="date", defaultValue = "") String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.parse(dateString, formatter);
        List<InsurancePolicy> policies = insurancePolicyService.filterByExpiryDateAfter(date);
        return ResponseEntity.ok(policies);
    }
    @GetMapping("/between")
    public ResponseEntity<List<InsurancePolicy>> filterByExpiryDateBetween(
        @RequestParam(name="start", defaultValue = "") String startDateString, @RequestParam(name="end", defaultValue = "") String endDateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate startDate = LocalDate.parse(startDateString, formatter);
        LocalDate endDate = LocalDate.parse(endDateString, formatter);
        List<InsurancePolicy> policies = insurancePolicyService.filterByExpiryDateBetween(startDate,endDate);
        return ResponseEntity.ok(policies);
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse> addPolicy(@Valid @RequestBody InsurancePolicyDTO insurancePolicyDTO){
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
            ApiResponse response;
            if (success) {
                response = new ApiResponse(policyType + " Policy added successfully");
                return new ResponseEntity<>(response,HttpStatus.CREATED);
            } else {
                response = new ApiResponse("Failed to add policy");

                return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(e.getMessage());
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
    }

}
