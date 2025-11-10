package org.isfce.pid.projetDispenses2526.web.dto;

import jakarta.validation.constraints.*;

public record AddCourseDTO(
        @NotBlank String etablissement,
        @NotBlank String code,
        @NotBlank String libelle,
        @Min(1) int ects,
        String urlProgramme
) { }