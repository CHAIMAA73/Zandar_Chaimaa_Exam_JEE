package ma.zandar.chaimaa.examjee.dtos;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;
import ma.zandar.chaimaa.examjee.entities.StatutContrat;
import java.util.Date;

@Data @NoArgsConstructor @AllArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ContratAutomobileDTO.class, name = "AUTOMOBILE"),
        @JsonSubTypes.Type(value = ContratHabitationDTO.class, name = "HABITATION"),
        @JsonSubTypes.Type(value = ContratSanteDTO.class, name = "SANTE")
})
public abstract class ContratAssuranceDTO {
    private Long id;
    private Date dateSouscription;
    private StatutContrat statut;
    private Date dateValidation;
    private Double montantCotisation;
    private Integer dureeContrat;
    private Double tauxCouverture;
    private Long clientId;
    private String clientNom;
}