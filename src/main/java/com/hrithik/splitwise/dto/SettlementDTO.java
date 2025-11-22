package com.hrithik.splitwise.dto;

import com.hrithik.splitwise.entities.Settlement;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class SettlementDTO {
    private Long id;
    private Long fromUserId;
    private Long toUserId;
    private BigDecimal amount;
    private Long groupId;
    private LocalDateTime settledAt;

    public SettlementDTO(Settlement settlement) {
        this.id = settlement.getId();
        this.fromUserId = settlement.getFromUser() != null ? settlement.getFromUser().getId() : null;
        this.toUserId = settlement.getToUser() != null ? settlement.getToUser().getId() : null;
        this.amount = settlement.getAmount();
        this.groupId = settlement.getGroup() != null ? settlement.getGroup().getId() : null;
        this.settledAt = settlement.getSettledAt();
    }
}
