package ma.zandar.chaimaa.examjee.repositories;

import ma.zandar.chaimaa.examjee.entities.ContratAutomobile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ContratAutomobileRepository extends JpaRepository<ContratAutomobile, Long> {
    Optional<ContratAutomobile> findByNumeroImmatriculation(String numero);
}