package com.hrithik.splitwise.repository;

import com.hrithik.splitwise.entities.Split;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SplitRepository extends JpaRepository<Split, Long> {
    List<Split> findByExpenseId(Long expenseId);
    List<Split> findByFromUserId(Long userId);
    List<Split> findByToUserId(Long userId);
    List<Split> findByFromUserIdOrToUserId(Long fromUserId, Long toUserId);
    List<Split> findByFromUserIdAndIsPaid(Long userId, Boolean isPaid);
}
