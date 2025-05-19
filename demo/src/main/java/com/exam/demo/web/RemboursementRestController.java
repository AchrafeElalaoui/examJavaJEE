package com.exam.demo.web;

import com.exam.demo.dtos.RemboursementDTO;
import com.exam.demo.entities.Remboursement.TypeRemboursement;
import com.exam.demo.exceptions.RemboursementNotFoundException;
import com.exam.demo.services.CreditService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/remboursements")
public class RemboursementRestController {
    
    private final CreditService creditService;

    @GetMapping("/{remboursementId}")
    public RemboursementDTO getRemboursement(@PathVariable Long remboursementId) 
            throws RemboursementNotFoundException {
        log.info("Récupération du remboursement avec l'id: {}", remboursementId);
        return creditService.getRemboursement(remboursementId);
    }

    @DeleteMapping("/{remboursementId}")
    public void deleteRemboursement(@PathVariable Long remboursementId) 
            throws RemboursementNotFoundException {
        log.info("Suppression du remboursement avec l'id: {}", remboursementId);
        creditService.deleteRemboursement(remboursementId);
    }




} 