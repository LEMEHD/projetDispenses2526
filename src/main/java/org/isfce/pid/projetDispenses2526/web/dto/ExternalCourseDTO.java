package org.isfce.pid.projetDispenses2526.web.dto;

import org.isfce.pid.projetDispenses2526.domain.ExternalCourse;
import java.util.UUID;

public record ExternalCourseDTO(
        UUID id,
        String etablissement,
        String code,
        String libelle,
        int ects,
        String urlProgramme
) {
    public static ExternalCourseDTO of(ExternalCourse c) {
        return new ExternalCourseDTO(
                c.getId(),
                c.getEtablissement(),
                c.getCode(),
                c.getLibelle(),
                c.getEcts(),
                c.getUrlProgramme()
        );
    }
}