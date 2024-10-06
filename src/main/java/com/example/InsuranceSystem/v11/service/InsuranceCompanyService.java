package com.example.InsuranceSystem.v11.service;

import java.util.*;

import org.springframework.stereotype.Service;
import java.time.*;
import com.example.InsuranceSystem.v11.entity.InsuranceCompany;
import com.example.InsuranceSystem.v11.entity.User;
import com.example.InsuranceSystem.v11.exception.InsuranceExceptions;
import com.example.InsuranceSystem.v11.repository.InsuranceCompanyRepository;
import com.example.InsuranceSystem.v11.repository.UserRepository;


@Service
public class InsuranceCompanyService {
    private final InsuranceCompanyRepository insuranceCompanyRepository;
    private final UserRepository userRepository;
    public InsuranceCompanyService(InsuranceCompanyRepository insuranceCompanyRepository, UserRepository userRepository){
        this.insuranceCompanyRepository = insuranceCompanyRepository;
        this.userRepository = userRepository;
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
    public  List<User> getAllUsers(){
        List<InsuranceCompany> companies = insuranceCompanyRepository.findAll(); 
        List<User> users = new ArrayList<>();
        for(InsuranceCompany company:companies){
            users = company.getUsers();
        }
        return users;
    }
    public Map<String, Integer> getPolicyHoldersByAgeRange(){
        List<User> users = userRepository.findAll();
        Map<String, Integer> ageRangeCount = new TreeMap<>();

        ageRangeCount.put("18-35", 0);
        ageRangeCount.put("36-50", 0);
        ageRangeCount.put("51+", 0);
        
        for(User user: users){
            int age = Period.between(user.getDob(),LocalDate.now()).getYears();
            if (age >= 18 && age <= 35) {
                ageRangeCount.put("18-35", ageRangeCount.get("18-35") + 1);
            } else if (age >=  36 && age <= 50) {
                ageRangeCount.put("36-50", ageRangeCount.get("36-50") + 1);
            } else if (age >= 51) {
                ageRangeCount.put("51+", ageRangeCount.get("51+") + 1);
            }
        }
        return ageRangeCount;
    }

    public Map<String, Integer> getStateReport(){
        List<User> users = userRepository.findAll();
        Map<String, Integer> stateCount = new HashMap<>();

        for(User user:users){
            String state = user.getAddress().getState();
            stateCount.put(state,stateCount.getOrDefault(state,0)+1);
        }
        return stateCount;
    }
}
