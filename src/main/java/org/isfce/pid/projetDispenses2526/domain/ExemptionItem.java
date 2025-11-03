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
}