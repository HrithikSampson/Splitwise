package com.hrithik.splitwise.repository;

import com.hrithik.splitwise.entities.Settlement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SettlementRepository extends JpaRepository<Settlement, Long> {
    List<Settlement> findByGroupId(Long groupId);
    List<Settlement> findByFromUserId(Long userId);
    List<Settlement> findByToUserId(Long userId);
    List<Settlement> findByFromUserIdOrToUserId(Long fromUserId, Long toUserId);
}
