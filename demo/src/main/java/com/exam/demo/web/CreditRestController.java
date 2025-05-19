package com.exam.demo.web;

import com.exam.demo.dtos.*;
import com.exam.demo.entities.Credit.StatutCredit;
import com.exam.demo.exceptions.ClientNotFoundException;
import com.exam.demo.exceptions.CreditNotFoundException;
import com.exam.demo.exceptions.InvalidRemboursementException;
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
@RequestMapping("/api/credits")
public class CreditRestController {
    
    private final CreditService creditService;

    // Endpoints pour les crédits personnels
    @PostMapping("/personnel/{clientId}")
    public CreditDTO saveCreditPersonnel(@PathVariable Long clientId, @RequestBody CreditPersonnelDTO creditDTO) 
            throws ClientNotFoundException {
        log.info("Création d'un nouveau crédit personnel pour le client: {}", clientId);
        return creditService.saveCreditPersonnel(creditDTO, clientId);
    }

    // Endpoints pour les crédits immobiliers
    @PostMapping("/immobilier/{clientId}")
    public CreditDTO saveCreditImmobilier(@PathVariable Long clientId, @RequestBody CreditImmobilierDTO creditDTO) 
            throws ClientNotFoundException {
        log.info("Création d'un nouveau crédit immobilier pour le client: {}", clientId);
        return creditService.saveCreditImmobilier(creditDTO, clientId);
    }

    // Endpoints pour les crédits professionnels
    @PostMapping("/professionnel/{clientId}")
    public CreditDTO saveCreditProfessionnel(@PathVariable Long clientId, @RequestBody CreditProfessionnelDTO creditDTO) 
            throws ClientNotFoundException {
        log.info("Création d'un nouveau crédit professionnel pour le client: {}", clientId);
        return creditService.saveCreditProfessionnel(creditDTO, clientId);
    }

    // Endpoints communs pour tous les types de crédits
    @GetMapping("/{creditId}")
    public CreditDTO getCredit(@PathVariable Long creditId) throws CreditNotFoundException {
        log.info("Récupération du crédit avec l'id: {}", creditId);
        return creditService.getCredit(creditId);
    }

    @GetMapping
    public List<CreditDTO> listCredits() {
        log.info("Récupération de la liste des crédits");
        return creditService.listCredits();
    }

    @DeleteMapping("/{creditId}")
    public void deleteCredit(@PathVariable Long creditId) throws CreditNotFoundException {
        log.info("Suppression du crédit avec l'id: {}", creditId);
        creditService.deleteCredit(creditId);
    }


    // Endpoints pour les remboursements
    @PostMapping("/{creditId}/remboursements")
    public RemboursementDTO saveRemboursement(@PathVariable Long creditId, @RequestBody RemboursementDTO remboursementDTO) 
            throws CreditNotFoundException, InvalidRemboursementException {
        log.info("Ajout d'un remboursement pour le crédit: {}", creditId);
        return creditService.saveRemboursement(remboursementDTO, creditId);
    }

    @GetMapping("/{creditId}/remboursements")
    public List<RemboursementDTO> getRemboursementsByCredit(@PathVariable Long creditId) 
            throws CreditNotFoundException {
        log.info("Récupération des remboursements pour le crédit: {}", creditId);
        return creditService.getRemboursementsByCredit(creditId);
    }

    // Endpoints pour les calculs
    @GetMapping("/{creditId}/montant-total")
    public double calculerMontantRemboursement(@PathVariable Long creditId) throws CreditNotFoundException {
        log.info("Calcul du montant total de remboursement pour le crédit: {}", creditId);
        return creditService.calculerMontantRemboursement(creditId);
    }

} 