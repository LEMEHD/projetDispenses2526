package org.isfce.pid.projetDispenses2526.web.dto;

import org.isfce.pid.projetDispenses2526.domain.ExemptionRequest;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record ExemptionRequestDTO(
        UUID id,
        String section,
        String statut,                 // DRAFT / SUBMITTED / ...
        Instant createdAt,
        Instant updatedAt,
        List<ExternalCourseDTO> externalCourses,
        List<SupportingDocumentDTO> documents,
        List<ExemptionItemDTO> items
) {
    public static ExemptionRequestDTO of(ExemptionRequest r) {
        List<ExternalCourseDTO> courses = r.getExternalCourses()
                .stream().map(ExternalCourseDTO::of).toList();
        List<SupportingDocumentDTO> docs = r.getDocuments()
                .stream().map(SupportingDocumentDTO::of).toList();
        List<ExemptionItemDTO> items = r.getItems()
                .stream().map(ExemptionItemDTO::of).toList();

        return new ExemptionRequestDTO(
                r.getId(),
                r.getSection(),
                r.getStatut().name(),
                r.getCreatedAt(),       // si @EnableJpaAuditing est activé
                r.getUpdatedAt(),
                courses,
                docs,
                items
        );
    }
}