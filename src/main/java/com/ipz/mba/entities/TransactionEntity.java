package com.ipz.mba.entities;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Table(name = "transactions")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sender_card_number")
    private String senderCardNumber;

    @Column(name = "receiver_card_number")
    private String receiverCardNumber;

    @Column(name = "purpose")
    private String purpose;

    @Column(name = "time")
    private ZonedDateTime time;

    @Column(name = "sum")
    private Long sum;

    @Column(name = "converted_sum")
    private BigDecimal convertedSum;

    public TransactionEntity(String senderCardNumber, String receiverCardNumber, String purpose, ZonedDateTime time, Long sum, BigDecimal convertedSum) {
        this.senderCardNumber = senderCardNumber;
        this.receiverCardNumber = receiverCardNumber;
        this.purpose = purpose;
        this.time = time;
        this.sum = sum;
        this.convertedSum = convertedSum;
    }
}