package com.example.InsuranceSystem.v11.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.InsuranceSystem.v11.entity.InsuranceCompany;
import com.example.InsuranceSystem.v11.exception.InsuranceExceptions;
import com.example.InsuranceSystem.v11.repository.InsuranceCompanyRepository;


@Service
public class InsuranceCompanyService {
    private final InsuranceCompanyRepository insuranceCompanyRepository;

    public InsuranceCompanyService(InsuranceCompanyRepository insuranceCompanyRepository){
        this.insuranceCompanyRepository = insuranceCompanyRepository;
    } 

    public InsuranceCompany createCompany(InsuranceCompany company) {
        long count = insuranceCompanyRepository.count();
        if(count > 0){
            throw new InsuranceExceptions.InsuranceCompanyExists("Insurance Company Exists");
        }
        return insuranceCompanyRepository.save(company);
    }
    public Optional<InsuranceCompany> findCompany(Long id){
        return insuranceCompanyRepository.findById(id);
    }
}
