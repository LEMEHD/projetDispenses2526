package org.isfce.pid.projetDispenses2526.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.isfce.pid.projetDispenses2526.domain.Etudiant;
import java.util.*;



public interface IEtudiantRepository extends JpaRepository<Etudiant, UUID> {
    Optional<Etudiant> findByEmail(String email);
}
