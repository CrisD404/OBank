package entity;

import jakarta.persistence.*;
import lombok.Data;
import util.Status;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public @Data class Atm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double availableMoney;

    @Enumerated(EnumType.STRING)
    private Status status = Status.AVAILABLE;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ATMTransaction> transaction = new ArrayList<>();
}
