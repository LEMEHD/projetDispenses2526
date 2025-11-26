package org.isfce.pid.projetDispenses2526.repository;

import java.util.UUID;

import org.isfce.pid.projetDispenses2526.domain.KbCorrespondenceRuleSource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICorrespondenceRuleSourceRepository extends JpaRepository<KbCorrespondenceRuleSource, UUID> {
}
