package org.isfce.pid.projetDispenses2526.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "etu", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Etudiant extends BaseEntity {

    @NotBlank @Email
    @Column(nullable = false, unique = true)
    private String email;

    private String prenom;
    private String nom;
}
