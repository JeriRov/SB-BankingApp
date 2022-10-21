package com.ipz.mba.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "customers")
@NoArgsConstructor @AllArgsConstructor
@Setter @Getter
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private UserEntity userEntity;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "customer_roles",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleEntity> roles;

    public CustomerEntity(String firstName, String lastName, Set<RoleEntity> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.roles = roles;
    }
}
