package org.isfce.pid.projetDispenses2526.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "kb_rule_target")
public class KbCorrespondenceRuleTarget extends BaseEntity {

    /**
     * Règle à laquelle appartient cette cible.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "rule_id", nullable = false)
    private KbCorrespondenceRule rule;

    /**
     * UE ISFCE obtenue si la règle est satisfaite.
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "ue_id", nullable = false)
    private UEIsfce ue;
}
