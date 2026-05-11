package ma.zandar.chaimaa.examjee.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ma.zandar.chaimaa.examjee.dtos.ClientDTO;
import ma.zandar.chaimaa.examjee.services.AssuranceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
@Tag(name = "Clients", description = "Gestion des clients")
@CrossOrigin(origins = "http://localhost:4200")
public class ClientController {

    private final AssuranceService assuranceService;

    @GetMapping
    @PreAuthorize("hasAnyRole('EMPLOYE','ADMIN')")
    @Operation(summary = "Lister tous les clients")
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        return ResponseEntity.ok(assuranceService.getAllClients());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CLIENT','EMPLOYE','ADMIN')")
    @Operation(summary = "Obtenir un client par ID")
    public ResponseEntity<ClientDTO> getClient(@PathVariable Long id) {
        return ResponseEntity.ok(assuranceService.getClient(id));
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('EMPLOYE','ADMIN')")
    @Operation(summary = "Rechercher des clients par nom")
    public ResponseEntity<List<ClientDTO>> searchClients(@RequestParam String nom) {
        return ResponseEntity.ok(assuranceService.searchClients(nom));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('EMPLOYE','ADMIN')")
    @Operation(summary = "Créer un nouveau client")
    public ResponseEntity<ClientDTO> createClient(@RequestBody ClientDTO clientDTO) {
        return new ResponseEntity<>(assuranceService.saveClient(clientDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('EMPLOYE','ADMIN')")
    @Operation(summary = "Modifier un client")
    public ResponseEntity<ClientDTO> updateClient(@PathVariable Long id, @RequestBody ClientDTO clientDTO) {
        return ResponseEntity.ok(assuranceService.updateClient(id, clientDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Supprimer un client")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        assuranceService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}