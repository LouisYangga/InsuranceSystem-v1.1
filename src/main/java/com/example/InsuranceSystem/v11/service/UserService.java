package com.example.InsuranceSystem.v11.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import com.example.InsuranceSystem.v11.entity.InsurancePolicy;
import com.example.InsuranceSystem.v11.entity.User;
import com.example.InsuranceSystem.v11.exception.InsuranceExceptions;
import com.example.InsuranceSystem.v11.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final InsurancePolicyService insurancePolicyService;

    public UserService(UserRepository userRepository, InsurancePolicyService insurancePolicyService){
        this.userRepository = userRepository;
        this.insurancePolicyService = insurancePolicyService;
    }
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }
    @Transactional
    public User saveUser(User user){
        userRepository.save(user);
        for(InsurancePolicy ins:user.getInsurancePolicies()){
            insurancePolicyService.saveInsurancePolicy(ins);
        }
        return user;
    }
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return user;
    }
    //CORE LOGIC
    public boolean addPolicy(InsurancePolicy insurancePolicy, Long userId){
        User user = findUserById(userId).orElseThrow(()->new InsuranceExceptions.UserNotFoundException("User not found"));
        boolean found = user.findPolicy(insurancePolicy);
        if(!found){
            insurancePolicyService.saveInsurancePolicy(insurancePolicy); // save the new policy into policy repo
            user.addPolicy(insurancePolicy); // add policy to found user 
            saveUser(user); // update user data in user repo
            return true;
        }else{
            return false;
        }
    }
    public double calcUserPayment(String username, int flatRate){
        User user = findByUsername(username);
        validateUser(user,username);
        return InsurancePolicyService.calcTotalPayment(user.getInsurancePolicies(), flatRate);
        
    }
    public double calcCostByType(String username, String policyType, int flatRate){
        User user = findByUsername(username);
        validateUser(user,username);
        List<InsurancePolicy> policies = insurancePolicyService.getInsurancePolicyByType(policyType);
        return InsurancePolicyService.calcTotalPayment(policies, flatRate);
    }
    public static void validateUser(User user, String username){
        if(user==null){
            throw new InsuranceExceptions.UserNotFoundException("User Not Found For: " + username);
        }
    }
}
