package com.exam.demo.dtos;

import com.exam.demo.entities.CreditImmobilier;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class CreditImmobilierDTO extends CreditDTO {
    private CreditImmobilier.TypeBien typeBien;
} 