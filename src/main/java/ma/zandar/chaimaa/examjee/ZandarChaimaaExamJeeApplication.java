package ma.zandar.chaimaa.examjee;

import ma.zandar.chaimaa.examjee.entities.*;
import ma.zandar.chaimaa.examjee.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class ZandarChaimaaExamJeeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZandarChaimaaExamJeeApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(
            ClientRepository clientRepository,
            ContratAssuranceRepository contratRepository,
            PaiementRepository paiementRepository,
            AppUserRepository userRepository,
            AppRoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {
        return args -> {
            // ========= ROLES =========
            AppRole roleAdmin = roleRepository.save(new AppRole(null, "ROLE_ADMIN"));
            AppRole roleEmploye = roleRepository.save(new AppRole(null, "ROLE_EMPLOYE"));
            AppRole roleClient = roleRepository.save(new AppRole(null, "ROLE_CLIENT"));

            // ========= USERS =========
            AppUser admin = new AppUser();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin1234"));
            admin.setRoles(List.of(roleAdmin, roleEmploye, roleClient));
            userRepository.save(admin);

            AppUser employe = new AppUser();
            employe.setUsername("employe1");
            employe.setPassword(passwordEncoder.encode("employe1234"));
            employe.setRoles(List.of(roleEmploye, roleClient));
            userRepository.save(employe);

            AppUser clientUser = new AppUser();
            clientUser.setUsername("client1");
            clientUser.setPassword(passwordEncoder.encode("client1234"));
            clientUser.setRoles(List.of(roleClient));
            userRepository.save(clientUser);

            // ========= CLIENTS =========
            Client c1 = clientRepository.save(Client.builder()
                    .nom("Alami Mohammed").email("alami@gmail.com").build());
            Client c2 = clientRepository.save(Client.builder()
                    .nom("Benali Fatima").email("benali@gmail.com").build());
            Client c3 = clientRepository.save(Client.builder()
                    .nom("Zahiri Karim").email("zahiri@gmail.com").build());

            // ========= CONTRATS AUTOMOBILE =========
            ContratAutomobile ca1 = new ContratAutomobile();
            ca1.setDateSouscription(new Date());
            ca1.setStatut(StatutContrat.EN_COURS);
            ca1.setMontantCotisation(1500.0);
            ca1.setDureeContrat(12);
            ca1.setTauxCouverture(80.0);
            ca1.setClient(c1);
            ca1.setNumeroImmatriculation("A-12345-B");
            ca1.setMarqueVehicule("Toyota");
            ca1.setModeleVehicule("Corolla");
            contratRepository.save(ca1);

            ContratAutomobile ca2 = new ContratAutomobile();
            ca2.setDateSouscription(new Date());
            ca2.setStatut(StatutContrat.VALIDE);
            ca2.setDateValidation(new Date());
            ca2.setMontantCotisation(2000.0);
            ca2.setDureeContrat(24);
            ca2.setTauxCouverture(90.0);
            ca2.setClient(c2);
            ca2.setNumeroImmatriculation("B-98765-C");
            ca2.setMarqueVehicule("Renault");
            ca2.setModeleVehicule("Clio");
            contratRepository.save(ca2);

            // ========= CONTRATS HABITATION =========
            ContratHabitation ch1 = new ContratHabitation();
            ch1.setDateSouscription(new Date());
            ch1.setStatut(StatutContrat.EN_COURS);
            ch1.setMontantCotisation(800.0);
            ch1.setDureeContrat(12);
            ch1.setTauxCouverture(75.0);
            ch1.setClient(c1);
            ch1.setTypeLogement(TypeLogement.APPARTEMENT);
            ch1.setAdresseLogement("12, Rue Hassan II, Casablanca");
            ch1.setSuperficie(90.0);
            contratRepository.save(ch1);

            ContratHabitation ch2 = new ContratHabitation();
            ch2.setDateSouscription(new Date());
            ch2.setStatut(StatutContrat.VALIDE);
            ch2.setDateValidation(new Date());
            ch2.setMontantCotisation(1200.0);
            ch2.setDureeContrat(36);
            ch2.setTauxCouverture(85.0);
            ch2.setClient(c3);
            ch2.setTypeLogement(TypeLogement.MAISON);
            ch2.setAdresseLogement("45, Avenue Mohammed V, Rabat");
            ch2.setSuperficie(200.0);
            contratRepository.save(ch2);

            // ========= CONTRATS SANTE =========
            ContratSante cs1 = new ContratSante();
            cs1.setDateSouscription(new Date());
            cs1.setStatut(StatutContrat.EN_COURS);
            cs1.setMontantCotisation(600.0);
            cs1.setDureeContrat(12);
            cs1.setTauxCouverture(70.0);
            cs1.setClient(c2);
            cs1.setNiveauCouverture(NiveauCouverture.INTERMEDIAIRE);
            cs1.setNombrePersonnesCouvertes(3);
            contratRepository.save(cs1);

            ContratSante cs2 = new ContratSante();
            cs2.setDateSouscription(new Date());
            cs2.setStatut(StatutContrat.VALIDE);
            cs2.setDateValidation(new Date());
            cs2.setMontantCotisation(1800.0);
            cs2.setDureeContrat(24);
            cs2.setTauxCouverture(100.0);
            cs2.setClient(c3);
            cs2.setNiveauCouverture(NiveauCouverture.PREMIUM);
            cs2.setNombrePersonnesCouvertes(5);
            contratRepository.save(cs2);

            // ========= PAIEMENTS =========
            List<ContratAssurance> contrats = contratRepository.findAll();
            for (ContratAssurance contrat : contrats) {
                Paiement p1 = new Paiement();
                p1.setDatePaiement(new Date());
                p1.setMontant(contrat.getMontantCotisation());
                p1.setTypePaiement(TypePaiement.MENSUALITE);
                p1.setContrat(contrat);
                paiementRepository.save(p1);

                Paiement p2 = new Paiement();
                p2.setDatePaiement(new Date());
                p2.setMontant(contrat.getMontantCotisation() * 12);
                p2.setTypePaiement(TypePaiement.PAIEMENT_ANNUEL);
                p2.setContrat(contrat);
                paiementRepository.save(p2);
            }

            System.out.println("====== Base de données initialisée avec succès ======");
            System.out.println("Clients: " + clientRepository.count());
            System.out.println("Contrats: " + contratRepository.count());
            System.out.println("Paiements: " + paiementRepository.count());
        };
    }
}