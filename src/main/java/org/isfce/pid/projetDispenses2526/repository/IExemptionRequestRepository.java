package org.isfce.pid.projetDispenses2526.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
import org.isfce.pid.projetDispenses2526.domain.ExemptionRequest;


public interface IExemptionRequestRepository extends JpaRepository<ExemptionRequest, UUID> {
    List<ExemptionRequest> findByEtudiantIdOrderByCreatedAtDesc(UUID etudiantId);
}