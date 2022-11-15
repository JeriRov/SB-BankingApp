package com.ipz.mba.security.models;

import com.ipz.mba.entities.CustomerEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public record CustomerDetails(CustomerEntity customer) implements UserDetails {

    public CustomerEntity getCustomer() {
        return customer;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        customer.getRoles().forEach(roleEntity -> authorities.add(new SimpleGrantedAuthority(roleEntity.getName())));
        return authorities;
    }

    @Override
    public String getPassword() {
        return customer.getUserEntity().getPassword();
    }

    @Override // we will return here phoneNumber or ipn
    public String getUsername() {
        return customer.getUserEntity().getPhoneNumber() == null ?
                customer.getUserEntity().getIpn() : customer.getUserEntity().getPhoneNumber();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}