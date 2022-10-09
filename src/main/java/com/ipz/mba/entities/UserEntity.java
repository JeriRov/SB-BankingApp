package com.ipz.mba.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserEntity {
    @Id
    private Long id;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "passport_number")
    private String passportNumber;

    @Column(name = "password")
    private String password;

    public UserEntity(String phoneNumber, String passportNumber, String password) {
        this.phoneNumber = phoneNumber;
        this.passportNumber = passportNumber;
        this.password = password;
    }
}