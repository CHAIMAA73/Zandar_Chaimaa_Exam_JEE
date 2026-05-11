package ma.zandar.chaimaa.examjee.repositories;

import ma.zandar.chaimaa.examjee.entities.ContratHabitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContratHabitationRepository extends JpaRepository<ContratHabitation, Long> {}
