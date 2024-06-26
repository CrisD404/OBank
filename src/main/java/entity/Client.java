package entity;

import interfaces.UserI;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
public @Data class Client implements UserI {
    @Id
    private Long id;

    private String name;

    private String lastName;

    private String phone;

    private String email;

    private String address;
}
