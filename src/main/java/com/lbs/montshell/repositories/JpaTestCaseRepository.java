package com.lbs.montshell.repositories;

import com.lbs.montshell.models.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JpaTestCaseRepository extends JpaRepository<TestCase, Long> {
    Optional<TestCase> findById(Long id);

    @Query("select t.input from TestCase t where t.id = :id")
    String findInputById(@Param("id") Long id);

    @Query("select t.output from TestCase t where t.id = :id")
    String findOutputById(@Param("id") Long id);
}
