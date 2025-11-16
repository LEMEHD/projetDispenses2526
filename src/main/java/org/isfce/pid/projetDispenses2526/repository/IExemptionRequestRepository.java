package org.isfce.pid.projetDispenses2526.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
import org.isfce.pid.projetDispenses2526.domain.ExemptionRequest;


public interface IExemptionRequestRepository extends JpaRepository<ExemptionRequest, UUID> {
        
    @EntityGraph(attributePaths = {"externalCourses", "documents", "items", "items.ue"})
    Optional<ExemptionRequest> findWithAllById(UUID id);
    
    @EntityGraph(attributePaths = {"externalCourses","documents","items","items.ue"})
    List<ExemptionRequest> findAllByEtudiantEmail(String email);
}