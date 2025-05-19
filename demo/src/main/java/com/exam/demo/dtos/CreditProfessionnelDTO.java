package com.exam.demo.dtos;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class CreditProfessionnelDTO extends CreditDTO {
    private String motif;
    private String raisonSociale;
} 