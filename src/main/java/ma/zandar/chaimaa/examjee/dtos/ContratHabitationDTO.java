package ma.zandar.chaimaa.examjee.dtos;

import lombok.*;
import ma.zandar.chaimaa.examjee.entities.TypeLogement;

@Data @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ContratHabitationDTO extends ContratAssuranceDTO {
    private TypeLogement typeLogement;
    private String adresseLogement;
    private Double superficie;
}