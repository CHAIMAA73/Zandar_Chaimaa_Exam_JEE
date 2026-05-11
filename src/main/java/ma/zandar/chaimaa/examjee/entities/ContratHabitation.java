package ma.zandar.chaimaa.examjee.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@DiscriminatorValue("HABITATION")
@Data @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ContratHabitation extends ContratAssurance {

    @Enumerated(EnumType.STRING)
    private TypeLogement typeLogement;

    private String adresseLogement;
    private Double superficie;
}