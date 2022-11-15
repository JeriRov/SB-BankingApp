package com.ipz.mba.entities;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "currency_types")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CurrencyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "currency_name")
    private String currencyName;

    @Column(name = "buying_exchange_rate")
    private BigDecimal buyingExchangeRate;

    @Column(name = "sales_exchange_rate")
    private BigDecimal salesExchangeRate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyEntity that = (CurrencyEntity) o;
        return id.equals(that.id) && currencyName.equals(that.currencyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, currencyName);
    }
}