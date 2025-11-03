package org.isfce.pid.projetDispenses2526.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "exemption_request")
public class ExemptionRequest extends BaseEntity {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Etudiant etudiant;

    @NotBlank @Column(nullable = false)
    private String section; // ex: Informatique

    @Enumerated(EnumType.STRING) @Column(nullable = false)
    @Builder.Default
    private StatutDemande statut = StatutDemande.DRAFT;

    @OneToMany(mappedBy = "request", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default @ToString.Exclude
    private List<ExternalCourse> externalCourses = new ArrayList<>();

    @OneToMany(mappedBy = "request", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default @ToString.Exclude
    private List<SupportingDocument> documents = new ArrayList<>();

    @OneToMany(mappedBy = "request", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default @ToString.Exclude
    private List<ExemptionItem> items = new ArrayList<>();

    // Helpers pratiques
    public void addExternalCourse(ExternalCourse c){ c.setRequest(this); externalCourses.add(c); }
    public void addDocument(SupportingDocument d){ d.setRequest(this); documents.add(d); }
    public void addItem(ExemptionItem i){ i.setRequest(this); items.add(i); }
}