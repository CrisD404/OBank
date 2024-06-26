package entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public @Data class SavingsAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double balance;

    private String currency;

    private String alias;

    @Embedded
    private Card card;

    private Integer privateKey;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AccountTransaction> transactions = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Client client;
}
