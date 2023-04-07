package com.lbs.montshell.repositories;

import com.lbs.montshell.models.Problem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaProblemRepository extends JpaRepository<Problem, Long> {
    Problem save(Problem problem);

    Page<Problem> findAll(Pageable pageable);

    Optional<Problem> findById(Long id);
}
