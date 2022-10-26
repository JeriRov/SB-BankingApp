package com.ipz.mba.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "card_types")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CardTypeEntity {
    @Id
    private Long id;

    @Column(name = "type_name")
    private String name;
}