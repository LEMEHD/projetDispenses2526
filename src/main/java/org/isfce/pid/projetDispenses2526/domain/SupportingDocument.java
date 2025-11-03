package org.isfce.pid.projetDispenses2526.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "supporting_document")
public class SupportingDocument extends BaseEntity {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @ToString.Exclude
    private ExemptionRequest request;

    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private TypeDocument type;

    @NotBlank @Column(nullable = false)
    private String urlStockage;
}