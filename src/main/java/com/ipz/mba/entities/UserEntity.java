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

    @Column(name = "ipn")
    private String ipn;

    @Column(name = "password")
    private String password;

    public UserEntity(String phoneNumber, String ipn, String password) {
        this.phoneNumber = phoneNumber;
        this.ipn = ipn;
        this.password = password;
    }
}