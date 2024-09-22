package com.example.InsuranceSystem.v11.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.InsuranceSystem.v11.entity.InsuranceCompany;

@Repository
public interface InsuranceCompanyRepository extends JpaRepository<InsuranceCompany, Long>  {
    Optional<InsuranceCompany> findById(Long id);
}
