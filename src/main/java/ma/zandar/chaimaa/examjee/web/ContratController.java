package ma.zandar.chaimaa.examjee.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ma.zandar.chaimaa.examjee.dtos.ContratAssuranceDTO;
import ma.zandar.chaimaa.examjee.entities.StatutContrat;
import ma.zandar.chaimaa.examjee.services.AssuranceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contrats")
@RequiredArgsConstructor
@Tag(name = "Contrats", description = "Gestion des contrats d'assurance")
@CrossOrigin(origins = "http://localhost:4200")
public class ContratController {

    private final AssuranceService assuranceService;

    @GetMapping
    @PreAuthorize("hasAnyRole('EMPLOYE','ADMIN')")
    @Operation(summary = "Lister tous les contrats")
    public ResponseEntity<List<ContratAssuranceDTO>> getAllContrats() {
        return ResponseEntity.ok(assuranceService.getAllContrats());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CLIENT','EMPLOYE','ADMIN')")
    @Operation(summary = "Obtenir un contrat par ID")
    public ResponseEntity<ContratAssuranceDTO> getContrat(@PathVariable Long id) {
        return ResponseEntity.ok(assuranceService.getContrat(id));
    }

    @GetMapping("/client/{clientId}")
    @PreAuthorize("hasAnyRole('CLIENT','EMPLOYE','ADMIN')")
    @Operation(summary = "Lister les contrats d'un client")
    public ResponseEntity<List<ContratAssuranceDTO>> getContratsByClient(@PathVariable Long clientId) {
        return ResponseEntity.ok(assuranceService.getContratsByClient(clientId));
    }

    @GetMapping("/statut/{statut}")
    @PreAuthorize("hasAnyRole('EMPLOYE','ADMIN')")
    @Operation(summary = "Filtrer les contrats par statut")
    public ResponseEntity<List<ContratAssuranceDTO>> getContratsByStatut(@PathVariable StatutContrat statut) {
        return ResponseEntity.ok(assuranceService.getContratsByStatut(statut));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('EMPLOYE','ADMIN')")
    @Operation(summary = "Créer un nouveau contrat")
    public ResponseEntity<ContratAssuranceDTO> createContrat(@RequestBody ContratAssuranceDTO dto) {
        return new ResponseEntity<>(assuranceService.saveContrat(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('EMPLOYE','ADMIN')")
    @Operation(summary = "Modifier un contrat")
    public ResponseEntity<ContratAssuranceDTO> updateContrat(@PathVariable Long id, @RequestBody ContratAssuranceDTO dto) {
        return ResponseEntity.ok(assuranceService.updateContrat(id, dto));
    }

    @PatchMapping("/{id}/valider")
    @PreAuthorize("hasAnyRole('EMPLOYE','ADMIN')")
    @Operation(summary = "Valider un contrat")
    public ResponseEntity<ContratAssuranceDTO> validerContrat(@PathVariable Long id) {
        return ResponseEntity.ok(assuranceService.validerContrat(id));
    }

    @PatchMapping("/{id}/resilier")
    @PreAuthorize("hasAnyRole('EMPLOYE','ADMIN')")
    @Operation(summary = "Résilier un contrat")
    public ResponseEntity<ContratAssuranceDTO> resilierContrat(@PathVariable Long id) {
        return ResponseEntity.ok(assuranceService.resilierContrat(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Supprimer un contrat")
    public ResponseEntity<Void> deleteContrat(@PathVariable Long id) {
        assuranceService.deleteContrat(id);
        return ResponseEntity.noContent().build();
    }
}