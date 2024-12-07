package com.ebanking.notificationsservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class KYC {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String  lastName;

    @Column(name = "id_type")
    private String idType;

    @Column(name = "country_of_issue")
    private String countryOfIssue;

    @Column(name = "id_number")
    private String idNumber;

    @Column(name = "id_expiration_date")
    private String idExpirationDate;

    @Column(name = "id_validity_date")
    private String idValidityDate;

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    @Column(name = "profession")
    private String profession;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "country_of_address")
    private String countryOfAddress;

    @Column(name = "legal_address")
    private String legalAddress;

    @Column(name = "city")
    private String city;

    @Column(name = "gsm")
    private String gsm;

    @Column(name = "email")
    private String email;

    // One-to-one relationship with Customer
    @OneToOne(mappedBy = "kyc", cascade = CascadeType.ALL)
    private Customer customer;

    // Constructors, getters, setters...
}
