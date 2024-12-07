package com.ebanking.notificationsservice.entity;

import com.ebanking.notificationsservice.model.CustomerType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"transactions", "wallets"})
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private  String cin;
    private String firstName;
    private String lastName;
    private String phone;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "kyc_id", referencedColumnName = "id")
    @JsonIgnore
    private KYC kyc;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TransferEntity> transactions;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Wallet> wallets;
    @Enumerated(EnumType.STRING)
    private CustomerType customerType;
    private String  otp ;
    private Long ctc;
    private String rib ;


    public Customer(String firstName, String lastName, String gsm, String idNumber, CustomerType type) {
        this.firstName=firstName;
        this.lastName=lastName;
        this.phone=gsm;
        this.cin= idNumber ;
        this.customerType=type;
    }

    // Constructors, getters, setters...
}
