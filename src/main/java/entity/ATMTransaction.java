package entity;

import jakarta.persistence.*;
import lombok.Data;
import util.TransactionTypeE;

import java.time.LocalDateTime;

@Entity
@Table
public @Data class ATMTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TransactionTypeE type;

    private Double amount;

    private LocalDateTime date;
}