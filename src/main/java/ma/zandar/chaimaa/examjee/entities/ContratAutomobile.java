package ma.zandar.chaimaa.examjee.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@DiscriminatorValue("AUTOMOBILE")
@Data @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ContratAutomobile extends ContratAssurance {

    private String numeroImmatriculation;
    private String marqueVehicule;
    private String modeleVehicule;
}