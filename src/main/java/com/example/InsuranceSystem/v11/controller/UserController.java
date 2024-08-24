package com.example.InsuranceSystem.v11.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.InsuranceSystem.v11.DTO.DynamicDTO;
import com.example.InsuranceSystem.v11.DTO.UserPaymentRequestDTO;
import com.example.InsuranceSystem.v11.entity.User;
import com.example.InsuranceSystem.v11.exception.InsuranceExceptions;
import com.example.InsuranceSystem.v11.exception.UsernameAlreadyExistsException;
import com.example.InsuranceSystem.v11.service.UserService;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        Optional<User> user = userService.findUserById(userId);
        return user.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User exists = userService.findByUsername(user.getUsername());
        if(exists !=null ){
            throw new UsernameAlreadyExistsException("Username Has Been Used");
        }
        
        User createdUser = userService.saveUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> findByUsername(@PathVariable String username){
        User user = userService.findByUsername(username);
        if(user==null){
            throw new InsuranceExceptions.UserNotFoundException("User Not Found");
        }
        return ResponseEntity.ok(user);
    }
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) { 
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    //CORE LOGIC
    @PostMapping("/payment")
    public ResponseEntity<Double> calcUserTotalPayment(@Valid @RequestBody UserPaymentRequestDTO userPaymentRequestDTO){
        double total = userService.calcUserPayment(userPaymentRequestDTO.getUsername(),userPaymentRequestDTO.getFlatRate());
        return ResponseEntity.ok(total);
    }
    @PostMapping("/paymentByType")
    public ResponseEntity<DynamicDTO> calcPaymentByType(@Valid @RequestBody UserPaymentRequestDTO userPaymentRequestDTO){
        String username = userPaymentRequestDTO.getUsername();
        String policyType = userPaymentRequestDTO.getPolicyType();
        int flatRate = userPaymentRequestDTO.getFlatRate();
        double total = userService.calcCostByType(username, policyType, flatRate);
        DynamicDTO data = new DynamicDTO();
        data.setField("username", username);
        data.setField("policyType", policyType);
        data.setField("totalCost", total);
        return ResponseEntity.ok(data);
    }

}