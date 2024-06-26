package entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.time.LocalDate;

@Embeddable
public @Data class Card {
    private String number;
    private LocalDate expirationDate;
    private Integer cvc;
}
