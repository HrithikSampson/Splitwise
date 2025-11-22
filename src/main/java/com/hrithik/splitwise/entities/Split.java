package com.hrithik.splitwise.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.math.BigDecimal;

@Entity
@Table(schema = "splitwise_main")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@NoArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class Split {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "expense_id", nullable = false)
    private Expense expense;

    @ManyToOne
    @JoinColumn(name = "from_user_id", nullable = false)
    private User fromUser;  // Who owes (debtor)

    @ManyToOne
    @JoinColumn(name = "to_user_id", nullable = false)
    private User toUser;    // Who is owed (creditor)

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private Boolean isPaid;

    @PrePersist
    private void onCreate() {
        if (isPaid == null) {
            isPaid = false;
        }
        validateUsers();
    }

    @PreUpdate
    private void onUpdate() {
        validateUsers();
    }

    private void validateUsers() {
        if (expense != null && expense.getGroup() != null) {
            if (fromUser != null && !expense.getGroup().getUsers().contains(fromUser)) {
                throw new IllegalStateException(
                        "Debtor (fromUser) must be a member of the group"
                );
            }
            if (toUser != null && !expense.getGroup().getUsers().contains(toUser)) {
                throw new IllegalStateException(
                        "Creditor (toUser) must be a member of the group"
                );
            }
        }
    }

    public com.hrithik.splitwise.dto.SplitDTO toDTO() {
        return new com.hrithik.splitwise.dto.SplitDTO(this);
    }
}
