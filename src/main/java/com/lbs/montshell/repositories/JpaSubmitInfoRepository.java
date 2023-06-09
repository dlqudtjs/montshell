package com.lbs.montshell.repositories;


import com.lbs.montshell.models.SubmitInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaSubmitInfoRepository extends JpaRepository<SubmitInfo, Long> {
    SubmitInfo save(SubmitInfo submitInfo);

    Page<SubmitInfo> findAll(Pageable pageable);

    Page<SubmitInfo> findByUserId(String userId, Pageable pageable);
}
