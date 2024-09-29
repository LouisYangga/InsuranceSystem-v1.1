package com.example.InsuranceSystem.v11.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.example.InsuranceSystem.v11.entity.InsuranceCompany;
import com.example.InsuranceSystem.v11.entity.User;
import com.example.InsuranceSystem.v11.service.InsuranceCompanyService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.*;


@Controller
@RequestMapping("company")
public class InsuranceCompanyController {
    private final InsuranceCompanyService insuranceCompanyService;

    public InsuranceCompanyController(InsuranceCompanyService insuranceCompanyService){
        this.insuranceCompanyService = insuranceCompanyService;
    }

    @PostMapping("/")
    public ResponseEntity<InsuranceCompany> createCompany(@RequestBody InsuranceCompany company) {
        InsuranceCompany found = insuranceCompanyService.createCompany(company);
        return ResponseEntity.ok(found);
    }
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(insuranceCompanyService.getAllUsers());
    }
    
    
}
