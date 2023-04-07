package com.lbs.montshell.services.problemServices;

import com.lbs.montshell.models.Problem;
import com.lbs.montshell.repositories.JpaProblemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProblemServiceImpl implements ProblemService{

    private final JpaProblemRepository problemRepository;

    public ProblemServiceImpl(JpaProblemRepository problemRepository) {
        this.problemRepository = problemRepository;
    }

    @Override
    public Problem save(Problem problem) {
        return problemRepository.save(problem);
    }

    @Override
    public Page<Problem> problemList(Pageable pageable) {
        return problemRepository.findAll(pageable);
    }

    @Override
    public Optional<Problem> findById(Long id) {
        return problemRepository.findById(id);
    }
}
