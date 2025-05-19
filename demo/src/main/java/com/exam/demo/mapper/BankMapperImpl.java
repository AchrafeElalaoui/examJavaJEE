package com.exam.demo.mapper;

import com.exam.demo.dtos.*;
import com.exam.demo.entities.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BankMapperImpl {

    // Client Mappings
    public ClientDTO fromClient(Client client) {
        ClientDTO clientDTO = new ClientDTO();
        BeanUtils.copyProperties(client, clientDTO);
        return clientDTO;
    }

    public Client fromClientDTO(ClientDTO clientDTO) {
        Client client = new Client();
        BeanUtils.copyProperties(clientDTO, client);
        return client;
    }

    // Credit Mappings
    public CreditImmobilierDTO fromCreditImmobilier(CreditImmobilier creditImmobilier) {
        CreditImmobilierDTO creditImmobilierDTO = new CreditImmobilierDTO();
        BeanUtils.copyProperties(creditImmobilier, creditImmobilierDTO);
        return creditImmobilierDTO;
    }
    public CreditPersonnelDTO fromCreditPersonnl(CreditPersonnel creditPersonnel) {
        CreditPersonnelDTO creditPersonnelDTO = new CreditPersonnelDTO();
        BeanUtils.copyProperties(creditPersonnel, creditPersonnelDTO );
        return creditPersonnelDTO;
    }
    public CreditProfessionnelDTO fromCreditProfessionnel(CreditProfessionnel creditProfessionnel) {
        CreditProfessionnelDTO creditProfessionnelDTO = new CreditProfessionnelDTO();
        BeanUtils.copyProperties(creditProfessionnel, creditProfessionnelDTO );
        return creditProfessionnelDTO;
    }



    public RemboursementDTO fromRemboursement(Remboursement remboursement) {
        RemboursementDTO remboursementDTO = new RemboursementDTO();
        BeanUtils.copyProperties(remboursement, remboursementDTO);
        remboursementDTO.setCreditId(remboursement.getCredit().getId());
        remboursementDTO.setTypeCredit(remboursement.getCredit().getClass().getSimpleName());
        return remboursementDTO;
    }

    public Remboursement fromRemboursementDTO(RemboursementDTO remboursementDTO) {
        Remboursement remboursement = new Remboursement();
        BeanUtils.copyProperties(remboursementDTO, remboursement);
        return remboursement;
    }

    // List Mappings
    public List<ClientDTO> fromClientList(List<Client> clients) {
        return clients.stream()
                .map(this::fromClient)
                .collect(Collectors.toList());
    }

    public List<CreditDTO> fromCreditList(List<Credit> credits) {
        return credits.stream()
                .map(this::fromCredit)
                .collect(Collectors.toList());
    }

    public List<RemboursementDTO> fromRemboursementList(List<Remboursement> remboursements) {
        return remboursements.stream()
                .map(this::fromRemboursement)
                .collect(Collectors.toList());
    }

    public CreditDTO fromCredit(Credit credit) {
        CreditDTO creditDTO;
        if (credit instanceof CreditPersonnel) {
            creditDTO = fromCreditPersonnl((CreditPersonnel) credit);
        } else if (credit instanceof CreditImmobilier) {
            creditDTO = fromCreditImmobilier((CreditImmobilier) credit);
        } else {
            creditDTO = fromCreditProfessionnel((CreditProfessionnel) credit);
        }
        return creditDTO;
    }
}