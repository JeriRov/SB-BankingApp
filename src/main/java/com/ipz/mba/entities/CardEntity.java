package com.ipz.mba.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ipz.mba.exceptions.*;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Table(name = "cards")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "owner")
public class CardEntity {
    @Id
    @Column(name = "card_number")
    private String cardNumber;

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id")
    private CustomerEntity owner;

    @Column(name = "created_at")
    private ZonedDateTime creationTime;

    @Column(name = "expiry_date")
    private ZonedDateTime expirationTime;

    @Column(name = "cvv_code")
    private String cvvCode;

    @Column(name = "pin_code")
    private String pinCode;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private CardTypeEntity cardType;

    @Column(name = "currency_name")
    private String currencyName;

    @ManyToOne
    @JoinColumn(name = "provider_id")
    private ProviderEntity providerEntity;

    @Column(name = "sum")
    private BigDecimal sum;

    @Column(name = "sum_limit")
    private Integer sumLimit;

    @Column(name = "blocked")
    private Boolean isBlocked;

    public static void validate(CardEntity card) throws CardNotActiveException {
        if (card.getExpirationTime().isBefore(ZonedDateTime.now())) {
            throw new CardNotActiveException(String.format("card %s is expired", card.getCardNumber().substring(12)));
        } else if (card.getIsBlocked()) {
            throw new CardNotActiveException(String.format("card %s is blocked", card.getCardNumber().substring(12)));
        }
    }

}