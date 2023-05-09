package com.lbs.montshell.services.submitService;

import com.lbs.montshell.models.Problem;
import com.lbs.montshell.models.SubmitInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface SubmitService {

    SubmitInfo save(SubmitInfo submitInfo);

    Page<SubmitInfo> getSubmitInfoList(Pageable pageable);

    Page<SubmitInfo> getSubmitInfoListByUserId(String user_id, Pageable pageable);

    Optional<SubmitInfo> getSubmitInfo(Long id);
}
