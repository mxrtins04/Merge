package com.merge.backend.scout.repository;

import com.merge.backend.scout.domain.ScoutAssessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScoutAssessmentRepository extends JpaRepository<ScoutAssessment, Long> {
    Optional<ScoutAssessment> findByStudentId(Long studentId);
    boolean existsByStudentId(Long studentId);
}
