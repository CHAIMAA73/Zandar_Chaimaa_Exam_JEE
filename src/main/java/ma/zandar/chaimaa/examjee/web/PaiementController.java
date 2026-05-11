package ma.zandar.chaimaa.examjee.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ma.zandar.chaimaa.examjee.dtos.PaiementDTO;
import ma.zandar.chaimaa.examjee.services.AssuranceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/paiements")
@RequiredArgsConstructor
@Tag(name = "Paiements", description = "Gestion des paiements")
@CrossOrigin(origins = "http://localhost:4200")
public class PaiementController {

    private final AssuranceService assuranceService;

    @GetMapping
    @PreAuthorize("hasAnyRole('EMPLOYE','ADMIN')")
    @Operation(summary = "Lister tous les paiements")
    public ResponseEntity<List<PaiementDTO>> getAllPaiements() {
        return ResponseEntity.ok(assuranceService.getAllPaiements());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CLIENT','EMPLOYE','ADMIN')")
    @Operation(summary = "Obtenir un paiement par ID")
    public ResponseEntity<PaiementDTO> getPaiement(@PathVariable Long id) {
        return ResponseEntity.ok(assuranceService.getPaiement(id));
    }

    @GetMapping("/contrat/{contratId}")
    @PreAuthorize("hasAnyRole('CLIENT','EMPLOYE','ADMIN')")
    @Operation(summary = "Lister les paiements d'un contrat")
    public ResponseEntity<List<PaiementDTO>> getPaiementsByContrat(@PathVariable Long contratId) {
        return ResponseEntity.ok(assuranceService.getPaiementsByContrat(contratId));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('EMPLOYE','ADMIN')")
    @Operation(summary = "Enregistrer un paiement")
    public ResponseEntity<PaiementDTO> createPaiement(@RequestBody PaiementDTO dto) {
        return new ResponseEntity<>(assuranceService.savePaiement(dto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Supprimer un paiement")
    public ResponseEntity<Void> deletePaiement(@PathVariable Long id) {
        assuranceService.deletePaiement(id);
        return ResponseEntity.noContent().build();
    }
}