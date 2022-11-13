package com.ipz.mba.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "provider_types")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProviderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "provider_name")
    private String providerName;

    @Column(name = "code")
    private String code; // first 8 digits
}

