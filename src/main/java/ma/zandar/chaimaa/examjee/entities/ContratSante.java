package ma.zandar.chaimaa.examjee.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@DiscriminatorValue("SANTE")
@Data @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ContratSante extends ContratAssurance {

    @Enumerated(EnumType.STRING)
    private NiveauCouverture niveauCouverture;

    private Integer nombrePersonnesCouvertes;
}