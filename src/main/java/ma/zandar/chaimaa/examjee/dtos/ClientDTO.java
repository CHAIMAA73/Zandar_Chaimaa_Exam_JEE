package ma.zandar.chaimaa.examjee.dtos;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ClientDTO {
    private Long id;
    private String nom;
    private String email;
    private int nombreContrats;
}