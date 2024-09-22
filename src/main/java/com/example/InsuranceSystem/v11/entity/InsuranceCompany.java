package com.example.InsuranceSystem.v11.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor

public class InsuranceCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private @Getter Long id;
    private @Getter @Setter String companyName;
    @OneToMany(mappedBy = "insuranceCompany")
    private @Getter @Setter List<User> users = new ArrayList<>();
    private @Getter @Setter int flatRate;

    public InsuranceCompany(String companyName, List<User> users, int flatRate){
        this.companyName = companyName;
        this.users = users;
        this.flatRate = flatRate;
    }
    public void addUser(User user){
        user.setInsuranceCompany(this);
        this.users.add(user);
    }
}
