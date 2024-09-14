package com.example.InsuranceSystem.v11.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.InsuranceSystem.v11.ApiResponse;
import com.example.InsuranceSystem.v11.DTO.DynamicDTO;
import com.example.InsuranceSystem.v11.DTO.PasswordDTO;
import com.example.InsuranceSystem.v11.DTO.UpdateUserDTO;
import com.example.InsuranceSystem.v11.DTO.UserPaymentRequestDTO;
import com.example.InsuranceSystem.v11.entity.InsurancePolicy;
import com.example.InsuranceSystem.v11.entity.User;
import com.example.InsuranceSystem.v11.exception.InsuranceExceptions;
import com.example.InsuranceSystem.v11.exception.UsernameAlreadyExistsException;
import com.example.InsuranceSystem.v11.service.UserService;

import jakarta.validation.Valid;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    @GetMapping("/id")
    public ResponseEntity<User> getUserById(@RequestParam (name="id",defaultValue = "0") Long userId) {
        Optional<User> user = userService.findUserById(userId);
        return user.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> findByUsername(@PathVariable String username){
        User user = userService.findByUsername(username);
        if(user==null){
            throw new InsuranceExceptions.UserNotFoundException("User Not Found");
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/model")
    public ResponseEntity<List<InsurancePolicy>> filterByCarModel(
        @RequestParam (name= "model", defaultValue = " ") String carModel,@RequestParam(name = "id", defaultValue = "0")Long userId) {
        return ResponseEntity.ok(userService.filterByCarsModel(userId, carModel));
    }
    
    @GetMapping("/carType")
    public ResponseEntity<List<InsurancePolicy>> filterByCarType(
        @RequestParam(name = "type",defaultValue = "") String type, @RequestParam(name = "id", defaultValue = "0")Long userId) {
        return ResponseEntity.ok(userService.filterByCarsType(userId, type));
    }
    
    @GetMapping("/after")
    public ResponseEntity<List<InsurancePolicy>> filterByExpiryDateAfter(
        @RequestParam(name = "date",defaultValue = "") String dateString, @RequestParam(name = "id", defaultValue = "0")Long userId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.parse(dateString, formatter);
        return ResponseEntity.ok(userService.filterByExpiryDateAfter(userId, date));
    }
    @GetMapping("/before")
    public ResponseEntity<List<InsurancePolicy>> filterByExpiryDateBefore(
        @RequestParam(name = "date",defaultValue = "") String dateString, @RequestParam(name = "id", defaultValue = "0")Long userId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.parse(dateString, formatter);
        return ResponseEntity.ok(userService.filterByExpiryDateBefore(userId, date));
    }
    @GetMapping("/between")
    public ResponseEntity<List<InsurancePolicy>> filterByExpiryDateAfter(
        @RequestParam(name = "start",defaultValue = "") String startDateString,@RequestParam(name = "end",defaultValue = "") String endDateString, @RequestParam(name = "id", defaultValue = "0")Long userId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate startDate = LocalDate.parse(startDateString, formatter);
        LocalDate endDate = LocalDate.parse(endDateString, formatter);
        return ResponseEntity.ok(userService.filterByExpiryDateBetween(userId, startDate,endDate));
    }
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User exists = userService.findByUsername(user.getUsername());
        if(exists !=null ){
            throw new UsernameAlreadyExistsException("Username Has Been Used");
        }
        
        User createdUser = userService.saveUser(user);
        return ResponseEntity.ok(createdUser);
    }

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

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody UpdateUserDTO updateUserDTO){
        User user = userService.updateUser(userId, updateUserDTO);
        return ResponseEntity.ok(user);
    }

    @PutMapping("password/{username}")
    public ResponseEntity<ApiResponse> updatePassword(@PathVariable String username, @RequestBody PasswordDTO passwordDTO) {
        String msg = userService.updatePassword(username, passwordDTO.getOldPassword(), passwordDTO.getNewPassword());
        ApiResponse response = new ApiResponse(msg);
        return new ResponseEntity<>(response,HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) { 
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}