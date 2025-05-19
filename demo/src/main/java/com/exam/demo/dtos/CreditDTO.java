package com.exam.demo.dtos;


import com.exam.demo.entities.Credit;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class CreditDTO {
    private Long id;
    private LocalDate dateDemande;
    private Credit.StatutCredit statut;
    private LocalDate dateAcceptation;
    private Double montant;
    private int dureeRemboursement;
    private Double tauxInteret;
    private Long clientId;
    private String clientNom;
} 