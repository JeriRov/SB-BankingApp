package com.ipz.mba.entities;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;

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

    @Column(name = "ipn")
    private String ipn;

    @Column(name = "passport_number")
    private String passportNumber;
    @Column(name = "password")
    private String password;

    public UserEntity(String phoneNumber, String ipn, String passportNumber, String password) {
        this.phoneNumber = phoneNumber;
        this.ipn = ipn;
        this.passportNumber = passportNumber;
        this.password = password;
    }
}