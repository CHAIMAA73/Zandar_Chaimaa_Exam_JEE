package ma.zandar.chaimaa.examjee.dtos;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ContratAutomobileDTO extends ContratAssuranceDTO {
    private String numeroImmatriculation;
    private String marqueVehicule;
    private String modeleVehicule;
}