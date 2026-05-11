package ma.zandar.chaimaa.examjee.repositories;

import ma.zandar.chaimaa.examjee.entities.ContratAssurance;
import ma.zandar.chaimaa.examjee.entities.StatutContrat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ContratAssuranceRepository extends JpaRepository<ContratAssurance, Long> {
    List<ContratAssurance> findByClientId(Long clientId);
    List<ContratAssurance> findByStatut(StatutContrat statut);
}