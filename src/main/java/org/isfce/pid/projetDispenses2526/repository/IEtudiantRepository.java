package org.isfce.pid.projetDispenses2526.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.isfce.pid.projetDispenses2526.domain.Student;
import java.util.*;



public interface IEtudiantRepository extends JpaRepository<Student, UUID> {
    Optional<Student> findByEmail(String email);
}
