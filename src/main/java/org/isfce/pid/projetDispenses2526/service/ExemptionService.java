package org.isfce.pid.projetDispenses2526.service;

import org.isfce.pid.projetDispenses2526.domain.*;
import org.isfce.pid.projetDispenses2526.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ExemptionService {

    private final IEtudiantRepository etuRepo;
    private final IUEIsfceRepository ueRepo;
    private final IExemptionRequestRepository reqRepo;
    private final IExternalCourseRepository courseRepo;
    private final ISupportingDocumentRepository docRepo;
    private final IExemptionItemRepository itemRepo;

    /** “Base de connaissances” simple : (ETAB::CODE) -> code UE ISFCE */
    private final Map<String, String> kb = new HashMap<>();

    private String key(String etab, String code) {
        return (etab + "::" + code).toUpperCase(Locale.ROOT);
    }
    public void seedRule(String etab, String codeExt, String ueCode) {
        kb.put(key(etab, codeExt), ueCode);
    }

    // ————— Flux Étudiant —————

    public Etudiant getOrCreateByEmail(String email) {
        return etuRepo.findByEmail(email).orElseGet(() -> etuRepo.save(Etudiant.builder().email(email).build()));
    }

    public ExemptionRequest createDraft(String email, String section) {
        Etudiant e = getOrCreateByEmail(email);
        ExemptionRequest req = ExemptionRequest.builder().etudiant(e).section(section).build();
        return reqRepo.save(req);
    }

    public ExternalCourse addExternalCourse(UUID requestId, String etab, String code, String libelle, int ects, String url) {
        ExemptionRequest req = reqRepo.findById(requestId).orElseThrow();
        ExternalCourse c = ExternalCourse.builder()
                .request(req).etablissement(etab).code(code).libelle(libelle).ects(ects).urlProgramme(url).build();
        c = courseRepo.save(c);
        req.addExternalCourse(c);
        return c;
    }

    public SupportingDocument addDocument(UUID requestId, TypeDocument type, String url) {
        ExemptionRequest req = reqRepo.findById(requestId).orElseThrow();
        SupportingDocument d = SupportingDocument.builder().request(req).type(type).urlStockage(url).build();
        d = docRepo.save(d);
        req.addDocument(d);
        return d;
    }

    public List<UEIsfce> listUE() { return ueRepo.findAll(); }

    public List<ExemptionRequest> myRequests(String email) {
        Etudiant e = getOrCreateByEmail(email);
        return reqRepo.findByEtudiantIdOrderByCreatedAtDesc(e.getId());
    }

    public ExemptionRequest get(UUID id) { return reqRepo.findById(id).orElseThrow(); }

    public ExemptionRequest submit(UUID id) {
        ExemptionRequest req = reqRepo.findById(id).orElseThrow();

        if (req.getExternalCourses().isEmpty()) throw new IllegalStateException("Ajoute au moins un cours externe.");
        if (req.getDocuments().isEmpty()) throw new IllegalStateException("Ajoute au moins un document.");

        // Auto-prédétermination : mappe les cours connus vers des UE
        for (ExternalCourse c : req.getExternalCourses()) {
            String ueCode = kb.get(key(c.getEtablissement(), c.getCode()));
            if (ueCode != null) {
                UEIsfce ue = ueRepo.findByCode(ueCode).orElse(null);
                if (ue != null) {
                    boolean ectsOk = c.getEcts() >= ue.getEcts();
                    ExemptionItem item = ExemptionItem.builder()
                            .request(req).ue(ue)
                            .totalEctsMatches(ectsOk)
                            .decision(ectsOk ? DecisionItem.AUTO_ACCEPTED : DecisionItem.NEEDS_REVIEW)
                            .build();
                    itemRepo.save(item);
                    req.addItem(item);
                }
            }
        }
        req.setStatut(StatutDemande.SUBMITTED);
        req.setUpdatedAt(Instant.now());
        return req;
    }
}
