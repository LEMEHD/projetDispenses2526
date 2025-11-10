package org.isfce.pid.projetDispenses2526.web.dto;

import jakarta.validation.constraints.*;

public record AddDocumentDTO(
        @NotBlank String type, // BULLETIN/PROGRAMME/MOTIVATION/AUTRE
        @NotBlank String url
) { }