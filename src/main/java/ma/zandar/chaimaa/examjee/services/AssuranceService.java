package ma.zandar.chaimaa.examjee.services;

import ma.zandar.chaimaa.examjee.dtos.*;
import ma.zandar.chaimaa.examjee.entities.StatutContrat;
import java.util.List;

public interface AssuranceService {

    // ===== CLIENT =====
    ClientDTO saveClient(ClientDTO clientDTO);
    ClientDTO updateClient(Long id, ClientDTO clientDTO);
    void deleteClient(Long id);
    ClientDTO getClient(Long id);
    List<ClientDTO> getAllClients();
    List<ClientDTO> searchClients(String nom);

    // ===== CONTRATS =====
    ContratAssuranceDTO saveContrat(ContratAssuranceDTO contratDTO);
    ContratAssuranceDTO updateContrat(Long id, ContratAssuranceDTO contratDTO);
    void deleteContrat(Long id);
    ContratAssuranceDTO getContrat(Long id);
    List<ContratAssuranceDTO> getAllContrats();
    List<ContratAssuranceDTO> getContratsByClient(Long clientId);
    List<ContratAssuranceDTO> getContratsByStatut(StatutContrat statut);
    ContratAssuranceDTO validerContrat(Long id);
    ContratAssuranceDTO resilierContrat(Long id);

    // ===== PAIEMENTS =====
    PaiementDTO savePaiement(PaiementDTO paiementDTO);
    PaiementDTO getPaiement(Long id);
    List<PaiementDTO> getPaiementsByContrat(Long contratId);
    List<PaiementDTO> getAllPaiements();
    void deletePaiement(Long id);
}