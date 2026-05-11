package ma.zandar.chaimaa.examjee.dtos;

import lombok.*;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class AuthResponseDTO {
    private String token;
    private String username;
    private List<String> roles;
}