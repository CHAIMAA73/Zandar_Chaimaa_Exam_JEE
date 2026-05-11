package ma.zandar.chaimaa.examjee.dtos;

import lombok.*;
import ma.zandar.chaimaa.examjee.entities.TypePaiement;
import java.util.Date;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class PaiementDTO {
    private Long id;
    private Date datePaiement;
    private Double montant;
    private TypePaiement typePaiement;
    private Long contratId;
}