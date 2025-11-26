package org.isfce.pid.projetDispenses2526.service;

import org.isfce.pid.projetDispenses2526.domain.*;
import org.isfce.pid.projetDispenses2526.repository.*;
import org.isfce.pid.projetDispenses2526.web.dto.ExemptionRequestDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ExemptionService {

    private final IEtudiantRepository etuRepo;
    private final IUEIsfceRepository ueRepo;
    private final IExemptionRequestRepository reqRepo;
    private final ICourseRepository courseRepo;
    private final IExternalCourseRepository extcourseRepo;
    private final ISupportingDocumentRepository docRepo;
    private final IExemptionItemRepository itemRepo;
    private final ICorrespondenceRuleRepository ruleRepo;

    /** “Base de connaissances” simple : (ETAB::CODE) -> code UE ISFCE */
    private final Map<String, String> kb = new HashMap<>();

    private String key(String etab, String code) {
        return (etab + "::" + code).toUpperCase(Locale.ROOT);
    }
    public void seedRule(String etab, String codeExt, String ueCode) {
        kb.put(key(etab, codeExt), ueCode);
    }

    // ————— Flux Étudiant —————

    public Student getOrCreateByEmail(String email) {
        return etuRepo.findByEmail(email).orElseGet(() -> etuRepo.save(Student.builder().email(email).build()));
    }

    public ExemptionRequest createDraft(String email, String section) {
        Student e = getOrCreateByEmail(email);
        ExemptionRequest req = ExemptionRequest.builder().etudiant(e).section(section).build();
        return reqRepo.save(req);
    }

    public ExternalCourse addExternalCourse(UUID requestId, String etab, String code, String libelle, int ects, String url) {
        ExemptionRequest req = reqRepo.findById(requestId).orElseThrow();
        // Empêcher la re-soumission
        if (req.getStatut() != StatutDemande.DRAFT) {
            throw new IllegalStateException(
                "Cette demande n'est plus modifiable : elle est déjà " + req.getStatut().name()
            );
        }
        ExternalCourse c = ExternalCourse.builder()
                .request(req).etablissement(etab).code(code).libelle(libelle).ects(ects).urlProgramme(url).build();
        c = extcourseRepo.save(c);
        req.addExternalCourse(c);
        return c;
    }

    public SupportingDocument addDocument(UUID requestId, TypeDocument type, String url) {
        ExemptionRequest req = reqRepo.findById(requestId).orElseThrow();
        // Empêcher la re-soumission
        if (req.getStatut() != StatutDemande.DRAFT) {
            throw new IllegalStateException(
                "Cette demande n'est plus modifiable : elle est déjà " + req.getStatut().name()
            );
        }
        SupportingDocument d = SupportingDocument.builder().request(req).type(type).urlStockage(url).build();
        d = docRepo.save(d);
        req.addDocument(d);
        return d;
    }

    public List<UEIsfce> listUE() { return ueRepo.findAll(); }

    @Transactional(readOnly = true)
    public List<ExemptionRequestDTO> myRequests(String email) {
        var list = reqRepo.findAllByEtudiantEmail(email);
        return list.stream()
                   .map(ExemptionRequestDTO::of)
                   .toList();
    }

    public ExemptionRequest get(UUID id) { return reqRepo.findById(id).orElseThrow(); }

    public ExemptionRequestDTO submit(UUID id) {
        ExemptionRequest req = reqRepo.findById(id).orElseThrow();
        
        // Empêcher la re-soumission
        if (req.getStatut() != StatutDemande.DRAFT) {
            throw new IllegalStateException(
                "Cette demande n'est plus modifiable : elle est déjà " + req.getStatut().name()
            );
        }

        // 1) vérifs
        if (req.getExternalCourses().isEmpty()) throw new IllegalStateException("Ajoute au moins un cours externe.");
        if (req.getDocuments().isEmpty()) throw new IllegalStateException("Ajoute au moins un document.");


        // 1) Reconnaître les cours externes dans la base de connaissances
        // ----------------------------------------------------------------
        // On garde:
        // - les KbCourse trouvés
        // - le lien KbCourse -> ExternalCourse (pour savoir quels cours de la demande ont servi)

        Map<KbCourse, List<ExternalCourse>> kbToExternal = new HashMap<>();
        Set<ExternalCourse> externalCourses = new HashSet<>(req.getExternalCourses());

        for (ExternalCourse ext : externalCourses) {
            String schoolCode = ext.getEtablissement(); // ex: "ULB", "VINCI", "HELB"

            // 1.1 Retrouver l'école
            Optional<KbSchool> optSchool = ISchoolRepository.findByCodeIgnoreCase(schoolCode);
            if (optSchool.isEmpty()) {
                // école inconnue de la KB -> ce cours restera "non couvert"
                continue;
            }
            KbSchool school = optSchool.get();

            // 1.2 Retrouver le cours dans la KB (école + code)
            Optional<KbCourse> optCourse = courseRepo
                    .findByEcoleAndCodeIgnoreCase(school, ext.getCode()); // méthode à créer si pas encore là

            if (optCourse.isEmpty()) {
                // cours inconnu pour cette école -> "non couvert"
                continue;
            }

            KbCourse kbCourse = optCourse.get();

            kbToExternal
                    .computeIfAbsent(kbCourse, k -> new ArrayList<>())
                    .add(ext);
        }

        // 2) Appliquer les règles de correspondance
        // ----------------------------------------------------------------
        // On détermine quelles règles sont applicables en fonction des KbCourse
        // présents, puis on crée les ExemptionItem correspondants.

        Set<KbSchool> schoolsInRequest = kbToExternal.keySet()
                .stream()
                .map(KbCourse::getEcole)
                .collect(Collectors.toSet());

        List<KbCorrespondenceRule> applicableRules = new ArrayList<>();

        for (KbSchool school : schoolsInRequest) {
            List<KbCorrespondenceRule> rulesForSchool =
                    ruleRepo.findByEcole(school); // ou findByEcole_CodeIgnoreCase(school.getCode())

            for (KbCorrespondenceRule rule : rulesForSchool) {

                // Vérifier que tous les KbCourse sources de la règle sont présents dans la demande
                boolean allSourcesPresent = rule.getSources()
                        .stream()
                        .map(KbCorrespondenceRuleSource::getCours)
                        .allMatch(kbToExternal::containsKey);

                if (!allSourcesPresent) {
                    continue;
                }

                // Calcul du total d'ECTS sur tous les cours sources
                int totalEcts = rule.getSources().stream()
                        .map(KbCorrespondenceRuleSource::getCours)
                        .mapToInt(KbCourse::getEcts)
                        .sum();

                boolean ectsOk = rule.getMinTotalEcts() == null
                        || totalEcts >= rule.getMinTotalEcts();

                // 2.1 Créer les ExemptionItem pour chaque UE cible
                for (KbCorrespondenceRuleTarget tgt : rule.getTargets()) {
                    UEIsfce ue = tgt.getUe();

                    ExemptionItem item = ExemptionItem.builder()
                            .request(req)
                            .ue(ue)
                            .totalEctsMatches(ectsOk)
                            .decision(ectsOk
                                    ? DecisionItem.AUTO_ACCEPTED
                                    : DecisionItem.NEEDS_REVIEW)
                            .build();

                    itemRepo.save(item);          // INCERTAINE: selon ta config cascade
                    req.getItems().add(item);     // ou méthode helper req.addItem(item)
                }

                applicableRules.add(rule);
            }
        }

        // 3) Déterminer quels ExternalCourse sont "couverts" par au moins une règle
        // ----------------------------------------------------------------
        Set<ExternalCourse> coveredExternalCourses = new HashSet<>();

        for (KbCorrespondenceRule rule : applicableRules) {
            for (KbCorrespondenceRuleSource src : rule.getSources()) {
                KbCourse kb = src.getCours();
                List<ExternalCourse> extCourses = kbToExternal.getOrDefault(kb, List.of());
                coveredExternalCourses.addAll(extCourses);
            }
        }

        // 4) Statut global de la demande : SUBMITTED vs UNDER_REVIEW
        // ----------------------------------------------------------------
        boolean auMoinsUnAutoAccepted = req.getItems().stream()
                .anyMatch(i -> i.getDecision() == DecisionItem.AUTO_ACCEPTED);

        boolean tousCoursCouverts = externalCourses.stream()
                .allMatch(coveredExternalCourses::contains);

        if (tousCoursCouverts && auMoinsUnAutoAccepted) {
            req.setStatut(StatutDemande.SUBMITTED); // la base de connaissances couvre tout → SUBMITTED
        } else {
            req.setStatut(StatutDemande.UNDER_REVIEW); // il reste des cas à traiter par le coordinateur
        }

        // 5) Sauvegarde et retour DTO
        ExemptionRequest saved = reqRepo.save(req);

        // INCERTAINE : adapte au mapper réel
        return ExemptionRequestDTO.of(saved);
    }
}
