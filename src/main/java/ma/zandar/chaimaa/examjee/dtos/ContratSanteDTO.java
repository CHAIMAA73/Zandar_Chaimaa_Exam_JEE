package ma.zandar.chaimaa.examjee.dtos;

import lombok.*;
import ma.zandar.chaimaa.examjee.entities.NiveauCouverture;

@Data @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ContratSanteDTO extends ContratAssuranceDTO {
    private NiveauCouverture niveauCouverture;
    private Integer nombrePersonnesCouvertes;
}