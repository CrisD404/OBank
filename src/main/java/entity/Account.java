package entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table
public @Data class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private Integer attempts = 0;
    private LocalDateTime lastLogin;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Employee employee;
}