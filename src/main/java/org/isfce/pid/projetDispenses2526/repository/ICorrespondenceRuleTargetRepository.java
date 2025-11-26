package org.isfce.pid.projetDispenses2526.repository;

import java.util.UUID;

import org.isfce.pid.projetDispenses2526.domain.KbCorrespondenceRuleTarget;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICorrespondenceRuleTargetRepository extends JpaRepository<KbCorrespondenceRuleTarget, UUID> {
}
