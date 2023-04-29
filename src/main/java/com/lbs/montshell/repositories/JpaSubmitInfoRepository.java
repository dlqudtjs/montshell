package com.lbs.montshell.repositories;


import com.lbs.montshell.models.SubmitInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaSubmitInfoRepository extends JpaRepository<SubmitInfo, Long> {
    SubmitInfo save(SubmitInfo submitInfo);

    Optional<SubmitInfo> findByUserId(Long user_id);
}
