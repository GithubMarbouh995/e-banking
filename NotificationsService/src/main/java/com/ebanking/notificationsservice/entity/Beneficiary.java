package com.ebanking.notificationsservice.entity;

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
public class Beneficiary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String phone;
    private String rib;
    private String cin;
    private  Long customerID;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonIgnoreProperties("beneficiaries")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "transfer_entity_id")
    private TransferEntity transferEntity;

    @OneToMany(mappedBy = "beneficiary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Wallet> wallets;
    private long transferID;
}
