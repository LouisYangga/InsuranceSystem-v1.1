package com.example.InsuranceSystem.v11.service;

import java.util.*;

import org.springframework.stereotype.Service;
import java.time.*;

import com.example.InsuranceSystem.v11.entity.ComprehensivePolicy;
import com.example.InsuranceSystem.v11.entity.InsuranceCompany;
import com.example.InsuranceSystem.v11.entity.InsurancePolicy;
import com.example.InsuranceSystem.v11.entity.ThirdPartyPolicy;
import com.example.InsuranceSystem.v11.entity.User;
import com.example.InsuranceSystem.v11.exception.InsuranceExceptions;
import com.example.InsuranceSystem.v11.repository.InsuranceCompanyRepository;
import com.example.InsuranceSystem.v11.repository.InsurancePolicyRepository;
import com.example.InsuranceSystem.v11.repository.UserRepository;


@Service
public class InsuranceCompanyService {
    private final InsuranceCompanyRepository insuranceCompanyRepository;
    private final UserRepository userRepository;
    private final InsurancePolicyRepository insurancePolicyRepository;
    public InsuranceCompanyService(InsuranceCompanyRepository insuranceCompanyRepository, UserRepository userRepository, InsurancePolicyRepository insurancePolicyRepository){
        this.insuranceCompanyRepository = insuranceCompanyRepository;
        this.userRepository = userRepository;
        this.insurancePolicyRepository = insurancePolicyRepository;
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
    public InsuranceCompany getCompany() {
        List<InsuranceCompany> companies = insuranceCompanyRepository.findAll();
        if (companies.size() > 1) {
            throw new IllegalStateException("More than one company exists in the system.");
        }
        return companies.isEmpty() ? null : companies.get(0);
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

    public Map<String, Integer> getCarTypeReport() {
        List<InsurancePolicy> policies = insurancePolicyRepository.findAll();
        Map<String,Integer> typeCount = new HashMap<>();

        for(InsurancePolicy policy:policies){
             String type = policy.getCar().getType().toString();
             typeCount.put(type, typeCount.getOrDefault(type,0)+1);
        }
        return typeCount;
    }

    public Map<String, Object> getGeneralReport(){
        // Give report about average age, average payment price.
        // amount of thirdparty and comprehensive insurance
        // total users and total insurance policies.
        Map<String, Object> report = new TreeMap<>();
        long totalUsers = userRepository.count();
        List<InsurancePolicy> policies = insurancePolicyRepository.findAll();
        List<User> users = userRepository.findAll();
        int comprehensiveCount = 0;
        int thirdPartyCount = 0;
        double totalPay = 0;
        int totalPolicies = policies.size();
        double totalAge = 0;
        
        for (InsurancePolicy policy : policies) {
            if (policy instanceof ComprehensivePolicy) {
                comprehensiveCount++;
            } else if (policy instanceof ThirdPartyPolicy) {
                thirdPartyCount++;
            }
            totalPay += policy.calculatePrice(getCompany().getFlatRate()); 
        }
        for(User user:users){
            int userAge = Period.between(user.getDob(), LocalDate.now()).getYears();
            totalAge += userAge;
        }

        double averageAge = totalPolicies > 0 ? totalAge / totalUsers : 0;

        double averagePayment = totalPolicies > 0 ? totalPay / totalPolicies : 0;

        // Add results to the report map
        report.put("totalUsers", totalUsers);
        report.put("totalPolicies", totalPolicies);
        report.put("comprehensiveCount", comprehensiveCount);
        report.put("thirdPartyCount", thirdPartyCount);
        report.put("averageAge", averageAge);
        report.put("averagePayment", averagePayment);
        
        return report;
    }
}
