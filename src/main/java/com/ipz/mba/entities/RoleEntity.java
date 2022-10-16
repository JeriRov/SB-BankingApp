package com.ipz.mba.entities;

import lombok.*;

import javax.persistence.*;
@Table(name = "roles")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RoleEntity {
    @Id
    private Long id;
    @Column(name = "name")
    private String name;
}