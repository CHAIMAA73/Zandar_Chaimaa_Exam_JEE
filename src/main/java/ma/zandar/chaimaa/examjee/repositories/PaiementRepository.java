package ma.zandar.chaimaa.examjee.repositories;

import ma.zandar.chaimaa.examjee.entities.Paiement;
import ma.zandar.chaimaa.examjee.entities.TypePaiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PaiementRepository extends JpaRepository<Paiement, Long> {
    List<Paiement> findByContratId(Long contratId);
    List<Paiement> findByTypePaiement(TypePaiement type);
}