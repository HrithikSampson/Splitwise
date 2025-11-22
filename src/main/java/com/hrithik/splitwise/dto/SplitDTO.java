package com.hrithik.splitwise.dto;

import com.hrithik.splitwise.entities.Split;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class SplitDTO {
    private Long id;
    private Long expenseId;
    private Long fromUserId;
    private Long toUserId;
    private BigDecimal amount;
    private Boolean isPaid;

    public SplitDTO(Split split) {
        this.id = split.getId();
        this.expenseId = split.getExpense() != null ? split.getExpense().getId() : null;
        this.fromUserId = split.getFromUser() != null ? split.getFromUser().getId() : null;
        this.toUserId = split.getToUser() != null ? split.getToUser().getId() : null;
        this.amount = split.getAmount();
        this.isPaid = split.getIsPaid();
    }
}
