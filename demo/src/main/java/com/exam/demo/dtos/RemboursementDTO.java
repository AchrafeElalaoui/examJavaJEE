package com.exam.demo.dtos;

import com.exam.demo.entities.Remboursement;
import lombok.Data;
import java.time.LocalDate;

@Data
public class RemboursementDTO {
    private Long id;
    private LocalDate date;
    private Double montant;
    private Remboursement.TypeRemboursement type;
    private Long creditId;
    private String typeCredit;
} 