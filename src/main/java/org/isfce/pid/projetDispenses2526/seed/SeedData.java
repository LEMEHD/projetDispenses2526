package org.isfce.pid.projetDispenses2526.seed;

import org.isfce.pid.projetDispenses2526.domain.*;
import org.isfce.pid.projetDispenses2526.repository.*;
import org.isfce.pid.projetDispenses2526.service.ExemptionService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeedData {

    @Bean
    ApplicationRunner runner(IUEIsfceRepository ueRepo,
                             ExemptionService svc,
                             ISchoolRepository schoolRepo,
                             ICourseRepository courseRepo,
                             ICorrespondenceRuleRepository ruleRepo) {

        return args -> {

            // 1) UE ISFCE (inchangé)
            if (ueRepo.count() == 0) {
                ueRepo.save(UEIsfce.builder().code("IPAP").libelle("Analyse informatique").ects(1).niveau(NiveauUE.N1).build());
                ueRepo.save(UEIsfce.builder().code("IPWD").libelle("Gestion et exploitation de bases de données").ects(1).niveau(NiveauUE.N1).build());
                ueRepo.save(UEIsfce.builder().code("WEB1").libelle("Initiation aux bases de données").ects(1).niveau(NiveauUE.N1).build());
                ueRepo.save(UEIsfce.builder().code("IBD").libelle("Mathématiques appliquées à l'informatique").ects(1).niveau(NiveauUE.N1).build());
                ueRepo.save(UEIsfce.builder().code("IPBD").libelle("Principes algorithmiques et programmation").ects(1).niveau(NiveauUE.N1).build());
                ueRepo.save(UEIsfce.builder().code("EBUSINESS").libelle("Informatique - Système d'exploitation").ects(1).niveau(NiveauUE.N1).build());
                ueRepo.save(UEIsfce.builder().code("ISO2").libelle("Structure des ordinateurs").ects(1).niveau(NiveauUE.N1).build());
                ueRepo.save(UEIsfce.builder().code("SYS2").libelle("Techniques de gestion de projets").ects(1).niveau(NiveauUE.N1).build());
                ueRepo.save(UEIsfce.builder().code("INFO101").libelle("Web principes de base").ects(1).niveau(NiveauUE.N1).build());
                ueRepo.save(UEIsfce.builder().code("RESO").libelle("Information et communication professionnelles").ects(1).niveau(NiveauUE.N1).build());
                ueRepo.save(UEIsfce.builder().code("MATH").libelle("Eléments de statistique ").ects(1).niveau(NiveauUE.N1).build());
                //--
//                ueRepo.save(UEIsfce.builder().code("4IBR2-1-A").libelle("Bases des réseaux").ects(1).niveau(NiveauUE.N2).build());
//                ueRepo.save(UEIsfce.builder().code("4INEB-1-A").libelle("Notion de e-business").ects(1).niveau(NiveauUE.N2).build());
//                ueRepo.save(UEIsfce.builder().code("4IPB2-1-A").libelle("Projet de développement SGBD").ects(1).niveau(NiveauUE.N2).build());
//                ueRepo.save(UEIsfce.builder().code("4IPC2-1-A").libelle("Projet d'analyse et de conception").ects(1).niveau(NiveauUE.N2).build());
//                ueRepo.save(UEIsfce.builder().code("4IPL3-1-A").libelle("Produits logiciels de gestion intégrés").ects(1).niveau(NiveauUE.N2).build());
//                ueRepo.save(UEIsfce.builder().code("4IPO3-1-A").libelle("Programmation orientée objet").ects(1).niveau(NiveauUE.N2).build());
//                ueRepo.save(UEIsfce.builder().code("4IPW3-1-A").libelle("Projet de développement Web").ects(1).niveau(NiveauUE.N2).build());
//                //--
//                ueRepo.save(UEIsfce.builder().code("4IAIT-1-A").libelle("Actualisation informatique et technique").ects(1).niveau(NiveauUE.N3).build());
//                ueRepo.save(UEIsfce.builder().code("4IAP1-1-A").libelle("Activités Professionnelles de Formation").ects(1).niveau(NiveauUE.N3).build());
//                ueRepo.save(UEIsfce.builder().code("4IAS4-1-A").libelle("Administration, gestion et sécurisation des réseaux et services").ects(1).niveau(NiveauUE.N3).build());
//                ueRepo.save(UEIsfce.builder().code("4IEI1-1-A").libelle("Epreuve intégrée de la section Informatique").ects(1).niveau(NiveauUE.N3).build());
//                ueRepo.save(UEIsfce.builder().code("4IEI1-2-A").libelle("Epreuve intégrée de la section Informatique").ects(1).niveau(NiveauUE.N3).build());
//                ueRepo.save(UEIsfce.builder().code("4IPI2-1-A").libelle("Projet d'intégration de développement").ects(1).niveau(NiveauUE.N3).build());
//                ueRepo.save(UEIsfce.builder().code("4ISP1-1-A").libelle("Stage d'intégration professionnel SIP").ects(1).niveau(NiveauUE.N3).build());
//                ueRepo.save(UEIsfce.builder().code("4ISP1-2-A").libelle("Stage d'intégration professionnel APF").ects(1).niveau(NiveauUE.N3).build());
//                ueRepo.save(UEIsfce.builder().code("4IVTE-1-A").libelle("Veille technologique").ects(1).niveau(NiveauUE.N3).build());
//                ueRepo.save(UEIsfce.builder().code("4XORG-3-A").libelle("Organisation des entreprises et éléments de management").ects(1).niveau(NiveauUE.N3).build());
            }

            // 2) Seed de la base de connaissances (écoles + cours + règles)
            if (schoolRepo.count() == 0 && courseRepo.count() == 0 && ruleRepo.count() == 0) {

                // 2.1 Écoles (5 établissements du PDF)
                KbSchool vinci = schoolRepo.save(KbSchool.builder()
                        .code("VINCI")
                        .etablissement("École Léonard de Vinci")
                        .urlProgramme("https://progcours.vinci.be/cocoon/programmes/P1INFV01_C.html")
                        .build());

                KbSchool ulb = schoolRepo.save(KbSchool.builder()
                        .code("ULB")
                        .etablissement("ULB Informatique")
                        .urlProgramme("https://www.ulb.be/fr/programme/ba-info-1#programme")
                        .build());

                KbSchool helb = schoolRepo.save(KbSchool.builder()
                        .code("HELB")
                        .etablissement("HELB Prigogine")
                        .urlProgramme("https://progcours.helb-prigogine.be/cocoon/programmes/SBIODA01_C.html")
                        .build());

                KbSchool he2b = schoolRepo.save(KbSchool.builder()
                        .code("HE2B")
                        .etablissement("HE2B ESI")
                        .urlProgramme("https://ects.esi-bru.be/online/grilles/ac2526_eeg.html")
                        .build());

                KbSchool ephec = schoolRepo.save(KbSchool.builder()
                        .code("EPHEC")
                        .etablissement("EPHEC")
                        // URL générique pour l'instant (tu pourras affiner)
                        .urlProgramme("https://www.ephec.be")
                        .build());

                // 2.2 Raccourcis vers les UE ISFCE (cibles des règles)
                UEIsfce ueIPAP = ueRepo.findByCode("IPAP").orElseThrow(); // IPAP
                UEIsfce ueIPWD = ueRepo.findByCode("IPWD").orElseThrow(); // ISO2
                UEIsfce ueMATH = ueRepo.findByCode("MATH").orElseThrow(); // IIBD
                UEIsfce ueIBD = ueRepo.findByCode("IBD").orElseThrow(); // ISE2
                UEIsfce ueIPBD = ueRepo.findByCode("IPBD").orElseThrow(); // IMA2
                UEIsfce ueRESO = ueRepo.findByCode("RESO").orElseThrow(); // IWPB (IPWB dans le PDF)

                // 2.3 Cours externes (on ne crée que ceux nécessaires à nos 3 exemples)

                // --- Cas 1 : 1 cours -> 1 UE (ULB INFO-F101 -> IPAP)
                KbCourse ulbIPAP = courseRepo.save(KbCourse.builder()
                        .ecole(ulb)
                        .code("ulbIPAP")
                        .libelle("Cours externe INFO-F101 (IPAP)") // libellé simple
                        .ects(10) // d'après le PDF
                        .urlProgramme("https://www.ulb.be/fr/programme/info-f101-1")
                        .build());
                // (on pourrait aussi créer INFO-F102 / INFO-H303 si tu veux compléter tout le tableau)

                // --- Cas 2 : 2 cours -> 1 UE (HELB IODA0101-2 + IODA0103-1 -> IPAP)
                KbCourse vinciIBD = courseRepo.save(KbCourse.builder()
                        .ecole(vinci)
                        .code("vinciIBD")
                        .libelle("Cours externe IODA0101-2 (IPAP)")
                        .ects(5)
                        .urlProgramme("https://progcours.helb-prigogine.be/cocoon/cours/IODA0101-2.html")
                        .build());

                KbCourse vinciIPBD = courseRepo.save(KbCourse.builder()
                        .ecole(vinci)
                        .code("vinciIPBD")
                        .libelle("Cours externe IODA0103-1 (IPAP)")
                        .ects(8)
                        .urlProgramme("https://progcours.helb-prigogine.be/cocoon/cours/IODA0103-1.html")
                        .build());

                // --- Cas 3 : 1 cours -> 2 UE (EXEMPLE SYNTHÉTIQUE, pas dans le PDF)
                // On crée un cours "fictif" HE2B qui combine Analyse (IPAI) + BD (IIBD) comme évoqué dans l'énoncé.
                KbCourse helbRESOMATH = courseRepo.save(KbCourse.builder()
                        .ecole(helb)
                        .code("helbRESOMATH") // code inventé pour illustrer
                        .libelle("Cours mixte Analyse + Bases de données (exemple interne)")
                        .ects(10)
                        .urlProgramme(null) // pas de fiche réelle
                        .build());


                // 2.4 RÈGLES

                // --- Règle 1 : ULB INFO-F101 -> IPAP (1 cours -> 1 UE)
                KbCorrespondenceRule ulbRuleIPAP = KbCorrespondenceRule.builder()
                        .ecole(ulb)
                        .description("ULBIPAP -> IPAP (1 cours -> 1 UE)")
                        .minTotalEcts(10) // on demande au moins 10 ECTS pour cette règle
                        .build();

                // source
                ulbRuleIPAP.addSource(
                        KbCorrespondenceRuleSource.builder()
                                .rule(ulbRuleIPAP)
                                .cours(ulbIPAP)
                                .build()
                );

                // cible
                ulbRuleIPAP.addTarget(
                        KbCorrespondenceRuleTarget.builder()
                                .rule(ulbRuleIPAP)
                                .ue(ueIPAP)
                                .build()
                );

                ruleRepo.save(ulbRuleIPAP);

                // --- Règle 2 : HELB IODA0101-2 + IODA0103-1 -> IPAP (2 cours -> 1 UE)
                KbCorrespondenceRule vinciruleIPBD = KbCorrespondenceRule.builder()
                        .ecole(vinci)
                        .description("vinciIPD + vinciIPBD -> IPBD (2 cours -> 1 UE)")
                        .minTotalEcts(13) // 5 + 8 d’après le tableau
                        .build();

                vinciruleIPBD.addSource(
                        KbCorrespondenceRuleSource.builder()
                                .rule(vinciruleIPBD)
                                .cours(vinciIBD)
                                .build()
                );
                vinciruleIPBD.addSource(
                        KbCorrespondenceRuleSource.builder()
                                .rule(vinciruleIPBD)
                                .cours(vinciIPBD)
                                .build()
                );

                vinciruleIPBD.addTarget(
                        KbCorrespondenceRuleTarget.builder()
                                .rule(vinciruleIPBD)
                                .ue(ueIPBD)
                                .build()
                );

                ruleRepo.save(vinciruleIPBD);

                // --- Règle 3 (EXEMPLE SYNTHÉTIQUE) : HE2B MIX-ANALYSE-BD -> IPAI + IIBD (1 cours -> 2 UE)
                KbCorrespondenceRule helbruleRESOMATH = KbCorrespondenceRule.builder()
                        .ecole(helb)
                        .description("HELB RESOMATH -> RESO + MATH (1 cours -> 2 UE, exemple)")
                        .minTotalEcts(10) // seuil arbitraire pour l'exemple
                        .build();

                helbruleRESOMATH.addSource(
                        KbCorrespondenceRuleSource.builder()
                                .rule(helbruleRESOMATH)
                                .cours(helbRESOMATH)
                                .build()
                );

                helbruleRESOMATH.addTarget(
                        KbCorrespondenceRuleTarget.builder()
                                .rule(helbruleRESOMATH)
                                .ue(ueRESO)
                                .build()
                );
                helbruleRESOMATH.addTarget(
                        KbCorrespondenceRuleTarget.builder()
                                .rule(helbruleRESOMATH)
                                .ue(ueMATH)
                                .build()
                );

                ruleRepo.save(helbruleRESOMATH);
            }

            // 3) Ancienne base mémoire pour l’auto-prédétermination (temporaire)
            //    => on la garde tant que submit() utilise encore la Map kb
            svc.seedRule("ULB", "INFO101", "IPDB");
            svc.seedRule("ULB", "ALG-BASE", "ALG1");
            svc.seedRule("HE2B", "WDEV-101", "WEB1");
        };
    }
}
