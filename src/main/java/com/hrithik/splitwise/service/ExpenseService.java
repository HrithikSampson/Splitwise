package com.hrithik.splitwise.service;

import com.hrithik.splitwise.dto.ExpenseDTO;
import com.hrithik.splitwise.entities.Expense;
import com.hrithik.splitwise.entities.Group;
import com.hrithik.splitwise.entities.User;
import com.hrithik.splitwise.repository.ExpenseRepository;
import com.hrithik.splitwise.repository.GroupRepository;
import com.hrithik.splitwise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    public ExpenseDTO createExpense(String description, BigDecimal amount, Long groupId, List<Long> payerIds) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found with id: " + groupId));

        List<User> payers = userRepository.findAllById(payerIds);
        if (payers.size() != payerIds.size()) {
            throw new IllegalArgumentException("Some payers not found");
        }

        for (User payer : payers) {
            if (!group.getUsers().contains(payer)) {
                throw new IllegalArgumentException("Payer " + payer.getUsername() + " is not a member of the group");
            }
        }

        Expense expense = new Expense();
        expense.getPayers().addAll(payers);

        expense = expenseRepository.save(expense);
        return expense.toDTO();
    }

    public ExpenseDTO getExpenseById(Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Expense not found with id: " + id));
        return expense.toDTO();
    }

    public List<ExpenseDTO> getExpensesByGroup(Long groupId) {
        return expenseRepository.findByGroupIdOrderByCreatedAtDesc(groupId).stream()
                .map(Expense::toDTO)
                .collect(Collectors.toList());
    }

    public List<ExpenseDTO> getExpensesByUser(Long userId) {
        return expenseRepository.findByPayersId(userId).stream()
                .map(Expense::toDTO)
                .collect(Collectors.toList());
    }

    public List<ExpenseDTO> getAllExpenses() {
        return expenseRepository.findAll().stream()
                .map(Expense::toDTO)
                .collect(Collectors.toList());
    }

    public void deleteExpense(Long id) {
        if (!expenseRepository.existsById(id)) {
            throw new IllegalArgumentException("Expense not found with id: " + id);
        }
        expenseRepository.deleteById(id);
    }
}
