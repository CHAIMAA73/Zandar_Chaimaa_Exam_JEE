package ma.zandar.chaimaa.examjee.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "TYPE_CONTRAT", discriminatorType = DiscriminatorType.STRING)
@Data @NoArgsConstructor @AllArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ContratAutomobile.class, name = "AUTOMOBILE"),
        @JsonSubTypes.Type(value = ContratHabitation.class, name = "HABITATION"),
        @JsonSubTypes.Type(value = ContratSante.class, name = "SANTE")
})
public abstract class ContratAssurance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date dateSouscription;

    @Enumerated(EnumType.STRING)
    private StatutContrat statut;

    @Temporal(TemporalType.DATE)
    private Date dateValidation;

    private Double montantCotisation;
    private Integer dureeContrat;
    private Double tauxCouverture;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "contrat", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Paiement> paiements;
}