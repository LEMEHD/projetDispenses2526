package org.isfce.pid.projetDispenses2526.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
import org.isfce.pid.projetDispenses2526.domain.ExternalCourse;


public interface IExternalCourseRepository extends JpaRepository<ExternalCourse, UUID> { }