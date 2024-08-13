package com.example.InsuranceSystem.v11.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.InsuranceSystem.v11.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
    User findByUsername(String username);
}
