package ma.zandar.chaimaa.examjee.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ma.zandar.chaimaa.examjee.dtos.AuthRequestDTO;
import ma.zandar.chaimaa.examjee.dtos.AuthResponseDTO;
import ma.zandar.chaimaa.examjee.security.services.JwtService;
import ma.zandar.chaimaa.examjee.security.services.UserDetailsServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentification", description = "API d'authentification JWT")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtService jwtService;

    @PostMapping("/login")
    @Operation(summary = "Connexion", description = "Authentifie un utilisateur et retourne un token JWT")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtService.generateToken(userDetails);

        AuthResponseDTO response = AuthResponseDTO.builder()
                .token(token)
                .username(userDetails.getUsername())
                .roles(userDetails.getAuthorities().stream()
                        .map(a -> a.getAuthority())
                        .collect(Collectors.toList()))
                .build();

        return ResponseEntity.ok(response);
    }
}