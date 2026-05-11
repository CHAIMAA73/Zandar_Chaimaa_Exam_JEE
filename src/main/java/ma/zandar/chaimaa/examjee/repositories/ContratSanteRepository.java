package ma.zandar.chaimaa.examjee.repositories;

import ma.zandar.chaimaa.examjee.entities.ContratSante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContratSanteRepository extends JpaRepository<ContratSante, Long> {}