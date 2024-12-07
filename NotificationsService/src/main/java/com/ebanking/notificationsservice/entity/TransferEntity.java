package com.ebanking.notificationsservice.entity;

import com.ebanking.notificationsservice.model.TransferState;
import com.ebanking.notificationsservice.model.TransferType;
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
@JsonIgnoreProperties("beneficiaries")
public class TransferEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String reference;
    private double amount;
    private double maxBAmountPeriodC;
    private int maxTransfersPerCustomer;
    private String toBeServedFrom;
    private String PINCode;
    private int maxPIN_Attempts;
    private int validationDuration;
    private String initiatedAt ;
    private  String  receiptUrl;  // change this and store  a binary  pdf
    @Lob
    private byte[] pdfContent;
    private Long benefeicaryID   ;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private String motif;

    @Enumerated(EnumType.STRING)
    private TransferState state;

    @Enumerated(EnumType.STRING)
    private TransferType type;

    @OneToOne
    @JoinColumn(name = "wallet_id", referencedColumnName = "id")
    @JsonIgnoreProperties("transfer")
    private Wallet wallet;
    @OneToMany(mappedBy = "transferEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Beneficiary> beneficiaries;
    private Long customerWalletId ;
    private Long beneficiaryWalletId;

}
