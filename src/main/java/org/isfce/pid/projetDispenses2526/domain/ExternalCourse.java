package org.isfce.pid.projetDispenses2526.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "external_course", indexes = @Index(columnList = "etablissement,code"))
public class ExternalCourse extends BaseEntity {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @ToString.Exclude
    private ExemptionRequest request;

    @NotBlank @Column(nullable = false)
    private String etablissement; // ULB, HE2B, ...

    @NotBlank @Column(nullable = false, length = 32)
    private String code;          // code du cours externe (clé de correspondance)

    @NotBlank @Column(nullable = false)
    private String libelle;

    @Min(1) @Column(nullable = false)
    private int ects;

    private String urlProgramme;  // lien/chemin vers la fiche
}