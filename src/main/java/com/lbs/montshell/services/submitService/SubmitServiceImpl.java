package com.lbs.montshell.services.submitService;

import com.lbs.montshell.models.Problem;
import com.lbs.montshell.models.SubmitInfo;
import com.lbs.montshell.repositories.JpaProblemRepository;
import com.lbs.montshell.repositories.JpaSubmitInfoRepository;
import com.lbs.montshell.services.problemServices.ProblemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubmitServiceImpl implements SubmitService {

   private JpaSubmitInfoRepository submitInfoRepository;

    public SubmitServiceImpl(JpaSubmitInfoRepository submitInfoRepository) {
        this.submitInfoRepository = submitInfoRepository;
    }

    @Override
    public Page<SubmitInfo> getSubmitInfoListByUserId(String userId, Pageable pageable) {
        return submitInfoRepository.findByUserId(userId, pageable);
    }

    @Override
    public SubmitInfo save(SubmitInfo submitInfo) {
        return submitInfoRepository.save(submitInfo);
    }

    @Override
    public Page<SubmitInfo> getSubmitInfoList(Pageable pageable) {
        return submitInfoRepository.findAll(pageable);
    }

    @Override
    public Optional<SubmitInfo> getSubmitInfo(Long id) {
        return Optional.empty();
    }
}
