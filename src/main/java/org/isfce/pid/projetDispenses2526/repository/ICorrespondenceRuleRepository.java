package org.isfce.pid.projetDispenses2526.repository;

import org.isfce.pid.projetDispenses2526.domain.KbCorrespondenceRule;
import org.isfce.pid.projetDispenses2526.domain.KbSchool;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ICorrespondenceRuleRepository extends JpaRepository<KbCorrespondenceRule, UUID> {

    List<KbCorrespondenceRule> findByEcole(KbSchool ecole);

    List<KbCorrespondenceRule> findByEcole_CodeIgnoreCase(String ecoleCode);
}
