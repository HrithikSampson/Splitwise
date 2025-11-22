package com.hrithik.splitwise.service;

import com.hrithik.splitwise.dto.SplitDTO;
import com.hrithik.splitwise.entities.Expense;
import com.hrithik.splitwise.entities.Split;
import com.hrithik.splitwise.entities.User;
import com.hrithik.splitwise.repository.ExpenseRepository;
import com.hrithik.splitwise.repository.SplitRepository;
import com.hrithik.splitwise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SplitService {

    @Autowired
    private SplitRepository splitRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    public SplitDTO createSplit(Long expenseId, Long fromUserId, Long toUserId, BigDecimal amount) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new IllegalArgumentException("Expense not found with id: " + expenseId));
        User fromUser = userRepository.findById(fromUserId)
                .orElseThrow(() -> new IllegalArgumentException("From user not found with id: " + fromUserId));
        User toUser = userRepository.findById(toUserId)
                .orElseThrow(() -> new IllegalArgumentException("To user not found with id: " + toUserId));

        Split split = new Split();
        split = splitRepository.save(split);
        return split.toDTO();
    }

    public SplitDTO getSplitById(Long id) {
        Split split = splitRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Split not found with id: " + id));
        return split.toDTO();
    }

    public List<SplitDTO> getSplitsByExpense(Long expenseId) {
        return splitRepository.findByExpenseId(expenseId).stream()
                .map(Split::toDTO)
                .collect(Collectors.toList());
    }

    public List<SplitDTO> getUserDebts(Long userId) {
        return splitRepository.findByFromUserId(userId).stream()
                .map(Split::toDTO)
                .collect(Collectors.toList());
    }

    public List<SplitDTO> getUserCredits(Long userId) {
        return splitRepository.findByToUserId(userId).stream()
                .map(Split::toDTO)
                .collect(Collectors.toList());
    }

    public List<SplitDTO> getUserUnpaidDebts(Long userId) {
        return splitRepository.findByFromUserIdAndIsPaid(userId, false).stream()
                .map(Split::toDTO)
                .collect(Collectors.toList());
    }

    public SplitDTO markSplitAsPaid(Long id) {
        Split split = splitRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Split not found with id: " + id));

        split = splitRepository.save(split);
        return split.toDTO();
    }

    public void deleteSplit(Long id) {
        if (!splitRepository.existsById(id)) {
            throw new IllegalArgumentException("Split not found with id: " + id);
        }
        splitRepository.deleteById(id);
    }
}
