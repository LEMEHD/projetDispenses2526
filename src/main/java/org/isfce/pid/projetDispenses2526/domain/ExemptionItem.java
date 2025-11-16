package org.isfce.pid.projetDispenses2526.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "exemption_item")
public class ExemptionItem extends BaseEntity {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @ToString.Exclude
    private ExemptionRequest request;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private UEIsfce ue; // UE visée

    @Builder.Default @Column(nullable = false)
    private boolean totalEctsMatches = false;

    @Enumerated(EnumType.STRING) @Column(nullable = false)
    @Builder.Default
    private DecisionItem decision = DecisionItem.PENDING;

    private Integer noteSur20;
    
 // Règles pour Set dans ExemptionRequest
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;                  // même instance
        if (o == null || getClass() != o.getClass()) return false;
        ExemptionItem other = (ExemptionItem) o;
        if (this.getId() == null || other.getId() == null) {
            // Avant persist: id null → NE PAS les considérer égaux
            return false;
        }
        return this.getId().equals(other.getId());
    }

    @Override
    public int hashCode() {
        // Avant persist (id null) : renvoyer un hash stable lié à la classe
        // Après persist : hash = id.hashCode()
        return (getId() == null) ? System.identityHashCode(this) : getId().hashCode();
    }
}