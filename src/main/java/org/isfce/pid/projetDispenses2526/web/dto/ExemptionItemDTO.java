package org.isfce.pid.projetDispenses2526.web.dto;

import org.isfce.pid.projetDispenses2526.domain.ExemptionItem;

import java.util.UUID;

public record ExemptionItemDTO(
        UUID id,
        String ueCode,
        String ueLibelle,
        int ueEcts,
        boolean totalEctsMatches,
        String decision,      // PENDING / AUTO_ACCEPTED / ...
        Integer noteSur20
) {
    public static ExemptionItemDTO of(ExemptionItem i) {
        return new ExemptionItemDTO(
                i.getId(),
                i.getUe().getCode(),
                i.getUe().getLibelle(),
                i.getUe().getEcts(),
                i.isTotalEctsMatches(),
                i.getDecision().name(),
                i.getNoteSur20()
        );
    }
}