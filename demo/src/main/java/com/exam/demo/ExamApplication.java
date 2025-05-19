package com.exam.demo;


import com.exam.demo.entities.*;
import com.exam.demo.repositories.ClientRepository;
import com.exam.demo.repositories.CreditRepository;
import com.exam.demo.repositories.RemboursementRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.time.LocalDate;
import java.util.Arrays;

@SpringBootApplication
public class ExamApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExamApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(
			ClientRepository clientRepository,
			CreditRepository creditRepository,
			RemboursementRepository remboursementRepository) {
		return args -> {
			// Création d'un client
			Client client = new Client();
			client.setNom("John Doe");
			client.setEmail("john.doe@email.com");
			clientRepository.save(client);
			System.out.println("Client créé : " + client);

			// Création d'un crédit personnel
			CreditPersonnel creditPersonnel = new CreditPersonnel();
			creditPersonnel.setClient(client);
			creditPersonnel.setDateDemande(LocalDate.now());
			creditPersonnel.setStatut(Credit.StatutCredit.EN_COURS);
			creditPersonnel.setMontant(10000.0);
			creditPersonnel.setDureeRemboursement(12);
			creditPersonnel.setTauxInteret(5.5);
			creditPersonnel.setMotif("Achat voiture");
			creditRepository.save(creditPersonnel);
			System.out.println("Crédit personnel créé : " + creditPersonnel);

			// Création d'un crédit immobilier
			CreditImmobilier creditImmobilier = new CreditImmobilier();
			creditImmobilier.setClient(client);
			creditImmobilier.setDateDemande(LocalDate.now());
			creditImmobilier.setStatut(Credit.StatutCredit.ACCEPTE);
			creditImmobilier.setDateAcceptation(LocalDate.now());
			creditImmobilier.setMontant(200000.0);
			creditImmobilier.setDureeRemboursement(240);
			creditImmobilier.setTauxInteret(3.5);
			creditImmobilier.setTypeBien(CreditImmobilier.TypeBien.APPARTEMENT);
			creditRepository.save(creditImmobilier);
			System.out.println("Crédit immobilier créé : " + creditImmobilier);

			// Création de remboursements
			Remboursement remboursement1 = new Remboursement();
			remboursement1.setCredit(creditPersonnel);
			remboursement1.setDate(LocalDate.now());
			remboursement1.setMontant(1000.0);
			remboursement1.setType(Remboursement.TypeRemboursement.MENSUALITE);

			Remboursement remboursement2 = new Remboursement();
			remboursement2.setCredit(creditImmobilier);
			remboursement2.setDate(LocalDate.now());
			remboursement2.setMontant(5000.0);
			remboursement2.setType(Remboursement.TypeRemboursement.REMBOURSEMENT_ANTICIPE);

			remboursementRepository.saveAll(Arrays.asList(remboursement1, remboursement2));
			System.out.println("Remboursements créés");


		};
	}
}