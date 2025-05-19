package com.exam.demo.web;

import com.exam.demo.dtos.ClientDTO;
import com.exam.demo.exceptions.ClientNotFoundException;
import com.exam.demo.services.CreditService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/clients")
public class ClientRestController {
    
    private final CreditService creditService;

    @GetMapping("/{clientId}")
    public ClientDTO getClient(@PathVariable Long clientId) throws ClientNotFoundException {
        log.info("Récupération du client avec l'id: {}", clientId);
        return creditService.getClient(clientId);
    }

    @GetMapping
    public List<ClientDTO> listClients() {
        log.info("Récupération de la liste des clients");
        return creditService.listClients();
    }

    @PostMapping
    public ClientDTO saveClient(@RequestBody ClientDTO clientDTO) {
        log.info("Création d'un nouveau client: {}", clientDTO);
        return creditService.saveClient(clientDTO);
    }

    @PutMapping("/{clientId}")
    public ClientDTO updateClient(@PathVariable Long clientId, @RequestBody ClientDTO clientDTO) 
            throws ClientNotFoundException {
        log.info("Mise à jour du client avec l'id: {}", clientId);
        clientDTO.setId(clientId);
        return creditService.updateClient(clientDTO);
    }

    @DeleteMapping("/{clientId}")
    public void deleteClient(@PathVariable Long clientId) throws ClientNotFoundException {
        log.info("Suppression du client avec l'id: {}", clientId);
        creditService.deleteClient(clientId);
    }

    @GetMapping("/search")
    public List<ClientDTO> searchClients(@RequestParam String keyword) {
        log.info("Recherche de clients avec le mot-clé: {}", keyword);
        return creditService.searchClients(keyword);
    }
} 