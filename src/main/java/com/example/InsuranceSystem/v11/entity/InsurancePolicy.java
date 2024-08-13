package com.example.InsuranceSystem.v11.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

// Add these annotations to your InsurancePolicy class
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "insuranceType")
@JsonSubTypes({
    @JsonSubTypes.Type(value = ComprehensivePolicy.class, name = "Comprehensive"),
    @JsonSubTypes.Type(value = ThirdPartyPolicy.class, name = "ThirdParty"),

})
@NoArgsConstructor
@Entity
@Table(name = "insurance_policies")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class InsurancePolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected @Getter Long id;
    @JsonIgnore
    protected @Getter @Setter String insuranceType;
    protected @Getter @Setter String policyHolderUsername;
    protected @Getter @Setter int numberOfClaims;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Ensure the foreign key column is named correctly
    @JsonIgnore
    private @Getter @Setter User user;

    @Embedded
    protected @Getter @Setter Car car;
    @Embedded
    protected @Getter @Setter MyDate expiryDate;

    public InsurancePolicy(String type, String policyHolderUsername,int numberofClaims, Car car, MyDate expiryDate){
        this.expiryDate = expiryDate;
        this.policyHolderUsername = policyHolderUsername;
        this.numberOfClaims = numberofClaims;
    }
    public abstract double calculatePrice(int flatRate);
}