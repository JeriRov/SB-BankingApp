package com.ipz.mba.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Table(name = "roles")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<CustomerEntity> customers;

    public RoleEntity(String name, List<CustomerEntity> customers) {
        this.name = name;
        this.customers = customers;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id: " + getId() + ", " +
                "name: " + name + "}";
    }
}