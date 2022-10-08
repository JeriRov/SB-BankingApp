package com.ipz.mba.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "customers")
@NoArgsConstructor @AllArgsConstructor
@Setter @Getter
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String firstName;

    @Column(name = "surname")
    private String lastName;

    @Column(name = "premium")
    private Boolean premium;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private UserEntity userEntity;

    public CustomerEntity(String firstName, String lastName, Boolean premium) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.premium = premium;
    }
}
