package com.example.InsuranceSystem.v11.entity;

import java.util.ArrayList;
    import java.util.List;

import jakarta.persistence.CascadeType;
    import jakarta.persistence.Embedded;
    import jakarta.persistence.Entity;
    import jakarta.persistence.GeneratedValue;
    import jakarta.persistence.GenerationType;
    import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
    import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;
    import lombok.ToString;
    @NoArgsConstructor
    @ToString

    @Entity
    @Table(name = "users")

    public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private @Getter Long userId;  
        private @Getter @Setter String name;
        @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
        @ToString.Exclude
        private @Getter @Setter List<InsurancePolicy> insurancePolicies = new ArrayList<>();
        @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
        private @Getter @Setter String username;
        // @JsonIgnore
        // @ToString.Exclude
        private @Getter @Setter String password;
        @Embedded
        private @Getter @Setter Address address;
        @ManyToOne
        @JoinColumn(name = "insurance_company_id")
        private @Getter @Setter InsuranceCompany insuranceCompany;
        public User(String name, String username, Address address, String password){
            this.name = name;
            this.username = username;
            this.address = address;
            this.password = password;
        }
        public boolean findPolicy(InsurancePolicy insurancePolicy){
            for(InsurancePolicy ins:this.insurancePolicies){
                if(ins.getId() == insurancePolicy.getId()){
                    return true;
                }
            }
            return false;
        }
        public void addPolicy(InsurancePolicy insurancePolicy) {
            insurancePolicy.setUser(this); // Set the user for the policy
            insurancePolicies.add(insurancePolicy);
        }   
    }
