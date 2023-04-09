package com.lbs.montshell.services.problemServices;

import com.lbs.montshell.models.Problem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProblemService {

    Problem save(Problem problem);

    Page<Problem> problemList(Pageable pageable);

    Optional<Problem> getProblem(Long id);
}
