package com.ipz.mba.security.services;

import com.ipz.mba.entities.CustomerEntity;
import com.ipz.mba.entities.UserEntity;
import com.ipz.mba.security.models.CustomerDetails;
import com.ipz.mba.repositories.CustomerRepository;
import com.ipz.mba.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerDetailsService(UserRepository userRepository, CustomerRepository customerRepository) {
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // here username can be phoneNumber or ipn, so we will search customer by phoneNumber at first and then by ipn
        Optional<UserEntity> userEntitySearchedByPhone = userRepository.findUserByPhoneNumber(username);
        Optional<UserEntity> userEntitySearchedByIpn = userRepository.findUserByIpn(username);

        if (userEntitySearchedByPhone.isEmpty() && userEntitySearchedByIpn.isEmpty()) {
            throw new UsernameNotFoundException("User was not found.");
        }
        UserEntity foundUser = (userEntitySearchedByPhone.isPresent() ?
                userEntitySearchedByPhone : userEntitySearchedByIpn).get();

        // find customer that relates to found user & return
        Optional<CustomerEntity> customerEntity = customerRepository.findById(foundUser.getId());
        return new CustomerDetails(customerEntity.orElseThrow(() -> new UsernameNotFoundException("Customer was not found")));
    }
}
