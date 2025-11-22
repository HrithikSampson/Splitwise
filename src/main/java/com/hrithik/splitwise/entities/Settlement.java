package com.hrithik.splitwise.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(schema = "splitwise_main")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@NoArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class Settlement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "from_user_id", nullable = false)
    private User fromUser;

    @ManyToOne
    @JoinColumn(name = "to_user_id", nullable = false)
    private User toUser;

    @Column(nullable = false)
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @Column(nullable = false, updatable = false)
    private LocalDateTime settledAt;

    @PrePersist
    public void onCreate() {
        this.settledAt = LocalDateTime.now();
    }

    public com.hrithik.splitwise.dto.SettlementDTO toDTO() {
        return new com.hrithik.splitwise.dto.SettlementDTO(this);
    }
}
