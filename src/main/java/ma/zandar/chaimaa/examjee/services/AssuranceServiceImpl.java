package ma.zandar.chaimaa.examjee.services;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.zandar.chaimaa.examjee.dtos.*;
import ma.zandar.chaimaa.examjee.entities.*;
import ma.zandar.chaimaa.examjee.repositories.*;
import ma.zandar.chaimaa.examjee.services.AssuranceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AssuranceServiceImpl implements AssuranceService {

    private final ClientRepository clientRepository;
    private final ContratAssuranceRepository contratRepository;
    private final ContratAutomobileRepository autoRepo;
    private final ContratHabitationRepository habitRepo;
    private final ContratSanteRepository santeRepo;
    private final PaiementRepository paiementRepository;

    // =================== CLIENT ===================

    @Override
    public ClientDTO saveClient(ClientDTO dto) {
        Client client = new Client();
        client.setNom(dto.getNom());
        client.setEmail(dto.getEmail());
        client = clientRepository.save(client);
        return mapClientToDTO(client);
    }

    @Override
    public ClientDTO updateClient(Long id, ClientDTO dto) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client non trouvé: " + id));
        client.setNom(dto.getNom());
        client.setEmail(dto.getEmail());
        client = clientRepository.save(client);
        return mapClientToDTO(client);
    }

    @Override
    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }

    @Override
    public ClientDTO getClient(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client non trouvé: " + id));
        return mapClientToDTO(client);
    }

    @Override
    public List<ClientDTO> getAllClients() {
        return clientRepository.findAll().stream()
                .map(this::mapClientToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClientDTO> searchClients(String nom) {
        return clientRepository.findByNomContainingIgnoreCase(nom).stream()
                .map(this::mapClientToDTO)
                .collect(Collectors.toList());
    }

    // =================== CONTRATS ===================

    @Override
    public ContratAssuranceDTO saveContrat(ContratAssuranceDTO dto) {
        Client client = clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new RuntimeException("Client non trouvé: " + dto.getClientId()));

        ContratAssurance contrat = buildContrat(dto, client);
        contrat = contratRepository.save(contrat);
        return mapContratToDTO(contrat);
    }

    @Override
    public ContratAssuranceDTO updateContrat(Long id, ContratAssuranceDTO dto) {
        ContratAssurance existing = contratRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contrat non trouvé: " + id));

        existing.setMontantCotisation(dto.getMontantCotisation());
        existing.setDureeContrat(dto.getDureeContrat());
        existing.setTauxCouverture(dto.getTauxCouverture());
        existing.setStatut(dto.getStatut());

        if (existing instanceof ContratAutomobile ca && dto instanceof ContratAutomobileDTO caDTO) {
            ca.setNumeroImmatriculation(caDTO.getNumeroImmatriculation());
            ca.setMarqueVehicule(caDTO.getMarqueVehicule());
            ca.setModeleVehicule(caDTO.getModeleVehicule());
        } else if (existing instanceof ContratHabitation ch && dto instanceof ContratHabitationDTO chDTO) {
            ch.setTypeLogement(chDTO.getTypeLogement());
            ch.setAdresseLogement(chDTO.getAdresseLogement());
            ch.setSuperficie(chDTO.getSuperficie());
        } else if (existing instanceof ContratSante cs && dto instanceof ContratSanteDTO csDTO) {
            cs.setNiveauCouverture(csDTO.getNiveauCouverture());
            cs.setNombrePersonnesCouvertes(csDTO.getNombrePersonnesCouvertes());
        }

        return mapContratToDTO(contratRepository.save(existing));
    }

    @Override
    public void deleteContrat(Long id) {
        contratRepository.deleteById(id);
    }

    @Override
    public ContratAssuranceDTO getContrat(Long id) {
        ContratAssurance contrat = contratRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contrat non trouvé: " + id));
        return mapContratToDTO(contrat);
    }

    @Override
    public List<ContratAssuranceDTO> getAllContrats() {
        return contratRepository.findAll().stream()
                .map(this::mapContratToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ContratAssuranceDTO> getContratsByClient(Long clientId) {
        return contratRepository.findByClientId(clientId).stream()
                .map(this::mapContratToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ContratAssuranceDTO> getContratsByStatut(StatutContrat statut) {
        return contratRepository.findByStatut(statut).stream()
                .map(this::mapContratToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ContratAssuranceDTO validerContrat(Long id) {
        ContratAssurance contrat = contratRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contrat non trouvé: " + id));
        contrat.setStatut(StatutContrat.VALIDE);
        contrat.setDateValidation(new Date());
        return mapContratToDTO(contratRepository.save(contrat));
    }

    @Override
    public ContratAssuranceDTO resilierContrat(Long id) {
        ContratAssurance contrat = contratRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contrat non trouvé: " + id));
        contrat.setStatut(StatutContrat.RESILIE);
        return mapContratToDTO(contratRepository.save(contrat));
    }

    // =================== PAIEMENTS ===================

    @Override
    public PaiementDTO savePaiement(PaiementDTO dto) {
        ContratAssurance contrat = contratRepository.findById(dto.getContratId())
                .orElseThrow(() -> new RuntimeException("Contrat non trouvé: " + dto.getContratId()));
        Paiement paiement = new Paiement();
        paiement.setDatePaiement(dto.getDatePaiement() != null ? dto.getDatePaiement() : new Date());
        paiement.setMontant(dto.getMontant());
        paiement.setTypePaiement(dto.getTypePaiement());
        paiement.setContrat(contrat);
        paiement = paiementRepository.save(paiement);
        return mapPaiementToDTO(paiement);
    }

    @Override
    public PaiementDTO getPaiement(Long id) {
        Paiement paiement = paiementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paiement non trouvé: " + id));
        return mapPaiementToDTO(paiement);
    }

    @Override
    public List<PaiementDTO> getPaiementsByContrat(Long contratId) {
        return paiementRepository.findByContratId(contratId).stream()
                .map(this::mapPaiementToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaiementDTO> getAllPaiements() {
        return paiementRepository.findAll().stream()
                .map(this::mapPaiementToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deletePaiement(Long id) {
        paiementRepository.deleteById(id);
    }

    // =================== MAPPERS ===================

    private ClientDTO mapClientToDTO(Client client) {
        ClientDTO dto = new ClientDTO();
        dto.setId(client.getId());
        dto.setNom(client.getNom());
        dto.setEmail(client.getEmail());
        dto.setNombreContrats(client.getContrats() != null ? client.getContrats().size() : 0);
        return dto;
    }

    private ContratAssuranceDTO mapContratToDTO(ContratAssurance contrat) {
        ContratAssuranceDTO dto;

        if (contrat instanceof ContratAutomobile ca) {
            ContratAutomobileDTO caDTO = new ContratAutomobileDTO();
            caDTO.setNumeroImmatriculation(ca.getNumeroImmatriculation());
            caDTO.setMarqueVehicule(ca.getMarqueVehicule());
            caDTO.setModeleVehicule(ca.getModeleVehicule());
            dto = caDTO;
        } else if (contrat instanceof ContratHabitation ch) {
            ContratHabitationDTO chDTO = new ContratHabitationDTO();
            chDTO.setTypeLogement(ch.getTypeLogement());
            chDTO.setAdresseLogement(ch.getAdresseLogement());
            chDTO.setSuperficie(ch.getSuperficie());
            dto = chDTO;
        } else if (contrat instanceof ContratSante cs) {
            ContratSanteDTO csDTO = new ContratSanteDTO();
            csDTO.setNiveauCouverture(cs.getNiveauCouverture());
            csDTO.setNombrePersonnesCouvertes(cs.getNombrePersonnesCouvertes());
            dto = csDTO;
        } else {
            throw new RuntimeException("Type de contrat inconnu");
        }

        dto.setId(contrat.getId());
        dto.setDateSouscription(contrat.getDateSouscription());
        dto.setStatut(contrat.getStatut());
        dto.setDateValidation(contrat.getDateValidation());
        dto.setMontantCotisation(contrat.getMontantCotisation());
        dto.setDureeContrat(contrat.getDureeContrat());
        dto.setTauxCouverture(contrat.getTauxCouverture());
        if (contrat.getClient() != null) {
            dto.setClientId(contrat.getClient().getId());
            dto.setClientNom(contrat.getClient().getNom());
        }
        return dto;
    }

    private PaiementDTO mapPaiementToDTO(Paiement paiement) {
        PaiementDTO dto = new PaiementDTO();
        dto.setId(paiement.getId());
        dto.setDatePaiement(paiement.getDatePaiement());
        dto.setMontant(paiement.getMontant());
        dto.setTypePaiement(paiement.getTypePaiement());
        if (paiement.getContrat() != null) {
            dto.setContratId(paiement.getContrat().getId());
        }
        return dto;
    }

    private ContratAssurance buildContrat(ContratAssuranceDTO dto, Client client) {
        ContratAssurance contrat;

        if (dto instanceof ContratAutomobileDTO caDTO) {
            ContratAutomobile ca = new ContratAutomobile();
            ca.setNumeroImmatriculation(caDTO.getNumeroImmatriculation());
            ca.setMarqueVehicule(caDTO.getMarqueVehicule());
            ca.setModeleVehicule(caDTO.getModeleVehicule());
            contrat = ca;
        } else if (dto instanceof ContratHabitationDTO chDTO) {
            ContratHabitation ch = new ContratHabitation();
            ch.setTypeLogement(chDTO.getTypeLogement());
            ch.setAdresseLogement(chDTO.getAdresseLogement());
            ch.setSuperficie(chDTO.getSuperficie());
            contrat = ch;
        } else if (dto instanceof ContratSanteDTO csDTO) {
            ContratSante cs = new ContratSante();
            cs.setNiveauCouverture(csDTO.getNiveauCouverture());
            cs.setNombrePersonnesCouvertes(csDTO.getNombrePersonnesCouvertes());
            contrat = cs;
        } else {
            throw new RuntimeException("Type de contrat inconnu");
        }

        contrat.setDateSouscription(dto.getDateSouscription() != null ? dto.getDateSouscription() : new Date());
        contrat.setStatut(dto.getStatut() != null ? dto.getStatut() : StatutContrat.EN_COURS);
        contrat.setMontantCotisation(dto.getMontantCotisation());
        contrat.setDureeContrat(dto.getDureeContrat());
        contrat.setTauxCouverture(dto.getTauxCouverture());
        contrat.setClient(client);
        return contrat;
    }
}