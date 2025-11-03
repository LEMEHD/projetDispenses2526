package org.isfce.pid.projetDispenses2526.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
import org.isfce.pid.projetDispenses2526.domain.UEIsfce;


public interface IUEIsfceRepository extends JpaRepository<UEIsfce, UUID> {
    Optional<UEIsfce> findByCode(String code);
}
