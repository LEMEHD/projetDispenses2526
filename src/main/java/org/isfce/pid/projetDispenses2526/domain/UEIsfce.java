package org.isfce.pid.projetDispenses2526.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "ue_isfce")
public class UEIsfce extends BaseEntity {

    @NotBlank
    @Column(nullable = false, unique = true, length = 16)
    private String code;   // ex: IPDB

    @NotBlank @Column(nullable = false)
    private String libelle;

    @Min(1) @Column(nullable = false)
    private int ects;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 2)
    private NiveauUE niveau;
}