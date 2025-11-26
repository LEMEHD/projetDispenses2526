package org.isfce.pid.projetDispenses2526.repository;

import org.isfce.pid.projetDispenses2526.domain.KbCourse;
import org.isfce.pid.projetDispenses2526.domain.KbSchool;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ICourseRepository extends JpaRepository<KbCourse, UUID> {

    Optional<KbCourse> findByEcoleAndCodeIgnoreCase(KbSchool ecole, String code);
}

