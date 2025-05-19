package com.exam.demo.services;

import com.exam.demo.dtos.*;
import com.exam.demo.entities.*;
import com.exam.demo.exceptions.*;
import com.exam.demo.mapper.BankMapperImpl;
import com.exam.demo.repositories.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class CreditServiceImpl implements CreditService {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CreditRepository creditRepository;
    @Autowired
    private RemboursementRepository remboursementRepository;
    @Autowired
    private BankMapperImpl bankMapper;

    // Implémentation des méthodes de gestion des clients
    @Override
    public ClientDTO saveClient(ClientDTO clientDTO) {
        Client client = bankMapper.fromClientDTO(clientDTO);
        Client savedClient = clientRepository.save(client);
        return bankMapper.fromClient(savedClient);
    }

    @Override
    public ClientDTO getClient(Long clientId) throws ClientNotFoundException {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ClientNotFoundException("Client non trouvé avec l'id: " + clientId));
        return bankMapper.fromClient(client);
    }

    @Override
    public List<ClientDTO> listClients() {
        List<Client> clients = clientRepository.findAll();
        return bankMapper.fromClientList(clients);
    }

    @Override
    public ClientDTO updateClient(ClientDTO clientDTO) throws ClientNotFoundException {
        if (!clientRepository.existsById(clientDTO.getId())) {
            throw new ClientNotFoundException("Client non trouvé avec l'id: " + clientDTO.getId());
        }
        Client client = bankMapper.fromClientDTO(clientDTO);
        Client updatedClient = clientRepository.save(client);
        return bankMapper.fromClient(updatedClient);
    }

    @Override
    public void deleteClient(Long clientId) throws ClientNotFoundException {
        if (!clientRepository.existsById(clientId)) {
            throw new ClientNotFoundException("Client non trouvé avec l'id: " + clientId);
        }
        clientRepository.deleteById(clientId);
    }

    @Override
    public List<ClientDTO> searchClients(String keyword) {
        List<Client> clients = clientRepository.findByNomContainingOrEmailContaining(keyword, keyword);
        return bankMapper.fromClientList(clients);
    }

    // Implémentation des méthodes de gestion des crédits
    @Override
    public CreditDTO saveCreditPersonnel(CreditPersonnelDTO creditDTO, Long clientId) throws ClientNotFoundException {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ClientNotFoundException("Client non trouvé avec l'id: " + clientId));
        
        CreditPersonnel credit = new CreditPersonnel();
        credit.setClient(client);
        credit.setDateDemande(LocalDate.now());
        credit.setStatut(Credit.StatutCredit.EN_COURS);
        credit.setMotif(creditDTO.getMotif());
        credit.setMontant(creditDTO.getMontant());
        credit.setDureeRemboursement(creditDTO.getDureeRemboursement());
        credit.setTauxInteret(creditDTO.getTauxInteret());

        Credit savedCredit = creditRepository.save(credit);
        return bankMapper.fromCredit(savedCredit);
    }

    @Override
    public CreditDTO saveCreditImmobilier(CreditImmobilierDTO creditDTO, Long clientId) throws ClientNotFoundException {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ClientNotFoundException("Client non trouvé avec l'id: " + clientId));
        
        CreditImmobilier credit = new CreditImmobilier();
        credit.setClient(client);
        credit.setDateDemande(LocalDate.now());
        credit.setStatut(Credit.StatutCredit.EN_COURS);
        credit.setTypeBien(creditDTO.getTypeBien());
        credit.setMontant(creditDTO.getMontant());
        credit.setDureeRemboursement(creditDTO.getDureeRemboursement());
        credit.setTauxInteret(creditDTO.getTauxInteret());

        Credit savedCredit = creditRepository.save(credit);
        return bankMapper.fromCredit(savedCredit);
    }

    @Override
    public CreditDTO saveCreditProfessionnel(CreditProfessionnelDTO creditDTO, Long clientId) throws ClientNotFoundException {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ClientNotFoundException("Client non trouvé avec l'id: " + clientId));
        
        CreditProfessionnel credit = new CreditProfessionnel();
        credit.setClient(client);
        credit.setDateDemande(LocalDate.now());
        credit.setStatut(Credit.StatutCredit.EN_COURS);
        credit.setMotif(creditDTO.getMotif());
        credit.setRaisonSociale(creditDTO.getRaisonSociale());
        credit.setMontant(creditDTO.getMontant());
        credit.setDureeRemboursement(creditDTO.getDureeRemboursement());
        credit.setTauxInteret(creditDTO.getTauxInteret());

        Credit savedCredit = creditRepository.save(credit);
        return bankMapper.fromCredit(savedCredit);
    }

    @Override
    public CreditDTO getCredit(Long creditId) throws CreditNotFoundException {
        Credit credit = creditRepository.findById(creditId)
                .orElseThrow(() -> new CreditNotFoundException("Crédit non trouvé avec l'id: " + creditId));
        return bankMapper.fromCredit(credit);
    }

    @Override
    public List<CreditDTO> listCredits() {
        List<Credit> credits = creditRepository.findAll();
        return bankMapper.fromCreditList(credits);
    }


    @Override
    public void deleteCredit(Long creditId) throws CreditNotFoundException {
        if (!creditRepository.existsById(creditId)) {
            throw new CreditNotFoundException("Crédit non trouvé avec l'id: " + creditId);
        }
        creditRepository.deleteById(creditId);
    }

    // Implémentation des méthodes de gestion des remboursements
    @Override
    public RemboursementDTO saveRemboursement(RemboursementDTO remboursementDTO, Long creditId) 
            throws CreditNotFoundException, InvalidRemboursementException {
        Credit credit = creditRepository.findById(creditId)
                .orElseThrow(() -> new CreditNotFoundException("Crédit non trouvé avec l'id: " + creditId));

        if (credit.getStatut() != Credit.StatutCredit.ACCEPTE) {
            throw new InvalidRemboursementException("Impossible d'ajouter un remboursement pour un crédit non accepté");
        }

        Remboursement remboursement = bankMapper.fromRemboursementDTO(remboursementDTO);
        remboursement.setCredit(credit);
        remboursement.setDate(LocalDate.now());

        Remboursement savedRemboursement = remboursementRepository.save(remboursement);
        return bankMapper.fromRemboursement(savedRemboursement);
    }

    @Override
    public List<RemboursementDTO> getRemboursementsByCredit(Long creditId) throws CreditNotFoundException {
        if (!creditRepository.existsById(creditId)) {
            throw new CreditNotFoundException("Crédit non trouvé avec l'id: " + creditId);
        }
        List<Remboursement> remboursements = remboursementRepository.findByCreditId(creditId);
        return bankMapper.fromRemboursementList(remboursements);
    }

    @Override
    public RemboursementDTO getRemboursement(Long remboursementId) throws RemboursementNotFoundException {
        Remboursement remboursement = remboursementRepository.findById(remboursementId)
                .orElseThrow(() -> new RemboursementNotFoundException("Remboursement non trouvé avec l'id: " + remboursementId));
        return bankMapper.fromRemboursement(remboursement);
    }

    @Override
    public void deleteRemboursement(Long remboursementId) throws RemboursementNotFoundException {
        if (!remboursementRepository.existsById(remboursementId)) {
            throw new RemboursementNotFoundException("Remboursement non trouvé avec l'id: " + remboursementId);
        }
        remboursementRepository.deleteById(remboursementId);
    }

    // Implémentation des méthodes de calcul
    @Override
    public double calculerMontantRemboursement(Long creditId) throws CreditNotFoundException {
        Credit credit = creditRepository.findById(creditId)
                .orElseThrow(() -> new CreditNotFoundException("Crédit non trouvé avec l'id: " + creditId));
        
        double montantTotal = credit.getMontant();
        double tauxMensuel = credit.getTauxInteret() / 100 / 12;
        int dureeMois = credit.getDureeRemboursement();
        
        // Calcul de la mensualité avec la formule : M = P * (r * (1 + r)^n) / ((1 + r)^n - 1)
        double mensualite = montantTotal * (tauxMensuel * Math.pow(1 + tauxMensuel, dureeMois)) 
                          / (Math.pow(1 + tauxMensuel, dureeMois) - 1);
        
        return mensualite * dureeMois;
    }


} 