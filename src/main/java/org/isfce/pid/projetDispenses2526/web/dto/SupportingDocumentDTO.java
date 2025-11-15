package org.isfce.pid.projetDispenses2526.web.dto;

import org.isfce.pid.projetDispenses2526.domain.SupportingDocument;

import java.util.UUID;

/** DTO renvoyé au client après ajout d'un document. */
public record SupportingDocumentDTO(
        UUID id,
        String type,          // BULLETIN / PROGRAMME / MOTIVATION / AUTRE
        String urlStockage
) {
    public static SupportingDocumentDTO of(SupportingDocument d) {
        return new SupportingDocumentDTO(
                d.getId(),
                d.getType().name(),
                d.getUrlStockage()
        );
    }
}