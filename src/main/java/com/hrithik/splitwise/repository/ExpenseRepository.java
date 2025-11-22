package com.hrithik.splitwise.repository;

import com.hrithik.splitwise.entities.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByGroupId(Long groupId);
    List<Expense> findByPayersId(Long userId);
    List<Expense> findByGroupIdOrderByCreatedAtDesc(Long groupId);
}
