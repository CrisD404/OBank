package entity;

import interfaces.UserI;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
public @Data class Employee implements UserI {
    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    private String lastName;
}
