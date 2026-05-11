package ma.zandar.chaimaa.examjee.dtos;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class AuthRequestDTO {
    private String username;
    private String password;
}
