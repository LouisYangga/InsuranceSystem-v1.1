package com.example.InsuranceSystem.v11.entity;

import java.util.ArrayList;
    import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
    import jakarta.persistence.Embedded;
    import jakarta.persistence.Entity;
    import jakarta.persistence.GeneratedValue;
    import jakarta.persistence.GenerationType;
    import jakarta.persistence.Id;
    import jakarta.persistence.OneToMany;
    import jakarta.persistence.Table;
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
        private @Getter @Setter int flatRate;
        private @Getter @Setter String username;
        @JsonIgnore
        private @Getter @Setter String password;
        @Embedded
        private @Getter @Setter Address address;

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
        // public boolean createThirdPartyPolicy(String policyKind, int id, Car car, int numberOfClaims, MyDate expiryDate, String comments) throws ExceptionHandling, PolicyNameException {
        //     return addPolicy(new ThirdPartyPolicy(policyKind, id, car, numberOfClaims, expiryDate, comments));
        // }
        // public void removePolicy(InsurancePolicy insurancePolicy) {
        //     insurancePolicies.remove(insurancePolicy);
        //     insurancePolicy.setUser(null); // Unset the user from the policy
        // }
        
    }
