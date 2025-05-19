package com.exam.demo.services;

import com.exam.demo.dtos.*;
import com.exam.demo.entities.Credit.StatutCredit;
import com.exam.demo.exceptions.*;

import java.time.LocalDate;
import java.util.List;

public interface CreditService {
    // Opérations sur les clients
    ClientDTO saveClient(ClientDTO clientDTO);
    ClientDTO getClient(Long clientId) throws ClientNotFoundException;
    List<ClientDTO> listClients();
    ClientDTO updateClient(ClientDTO clientDTO) throws ClientNotFoundException;
    void deleteClient(Long clientId) throws ClientNotFoundException;
    List<ClientDTO> searchClients(String keyword);

    // Opérations sur les crédits
    CreditDTO saveCreditPersonnel(CreditPersonnelDTO creditDTO, Long clientId) throws ClientNotFoundException;
    CreditDTO saveCreditImmobilier(CreditImmobilierDTO creditDTO, Long clientId) throws ClientNotFoundException;
    CreditDTO saveCreditProfessionnel(CreditProfessionnelDTO creditDTO, Long clientId) throws ClientNotFoundException;
    
    CreditDTO getCredit(Long creditId) throws CreditNotFoundException;
    List<CreditDTO> listCredits();
    void deleteCredit(Long creditId) throws CreditNotFoundException;

    // Opérations sur les remboursements
    RemboursementDTO saveRemboursement(RemboursementDTO remboursementDTO, Long creditId) 
        throws CreditNotFoundException, InvalidRemboursementException;
    
    List<RemboursementDTO> getRemboursementsByCredit(Long creditId) throws CreditNotFoundException;

    RemboursementDTO getRemboursement(Long remboursementId) throws RemboursementNotFoundException;
    void deleteRemboursement(Long remboursementId) throws RemboursementNotFoundException;

    // Opérations de calcul
    double calculerMontantRemboursement(Long creditId) throws CreditNotFoundException;
}