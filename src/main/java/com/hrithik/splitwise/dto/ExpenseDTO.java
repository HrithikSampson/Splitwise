package com.hrithik.splitwise.dto;

import com.hrithik.splitwise.entities.Expense;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ExpenseDTO {
    private Long id;
    private String description;
    private BigDecimal amount;
    private Long groupId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ExpenseDTO(Expense expense) {
        this.id = expense.getId();
        this.description = expense.getDescription();
        this.amount = expense.getAmount();
        this.groupId = expense.getGroup() != null ? expense.getGroup().getId() : null;
        this.createdAt = expense.getCreatedAt();
        this.updatedAt = expense.getUpdatedAt();
    }
}
