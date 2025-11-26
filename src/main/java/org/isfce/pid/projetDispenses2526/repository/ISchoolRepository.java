package org.isfce.pid.projetDispenses2526.repository;

import org.isfce.pid.projetDispenses2526.domain.KbSchool;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ISchoolRepository extends JpaRepository<KbSchool, UUID> {

    static Optional<KbSchool> findByCodeIgnoreCase(String code) {
		// TODO Auto-generated method stub
		return null;
	}
}
