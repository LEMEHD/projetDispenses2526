package org.isfce.pid.projetDispenses2526.seed;

import org.isfce.pid.projetDispenses2526.domain.UEIsfce;
import org.isfce.pid.projetDispenses2526.repository.IUEIsfceRepository;
import org.isfce.pid.projetDispenses2526.service.ExemptionService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeedData {

    @Bean
    ApplicationRunner runner(IUEIsfceRepository ueRepo, ExemptionService svc) {
        return args -> {
            if (ueRepo.count() == 0) {
                ueRepo.save(UEIsfce.builder().code("IPDB").libelle("Intro Prog & DB").ects(5).build());
                ueRepo.save(UEIsfce.builder().code("ALG1").libelle("Algorithmes 1").ects(4).build());
                ueRepo.save(UEIsfce.builder().code("WEB1").libelle("Web 1").ects(5).build());
            }
            // Règles simples : (établissement, code externe) -> code UE ISFCE
            svc.seedRule("ULB", "INFO101", "IPDB");
            svc.seedRule("ULB", "ALG-BASE", "ALG1");
            svc.seedRule("HE2B", "WDEV-101", "WEB1");
        };
    }
}
