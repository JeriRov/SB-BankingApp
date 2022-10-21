package com.ipz.mba.services.impl;

import com.ipz.mba.entities.CustomerEntity;
import com.ipz.mba.entities.RoleEntity;
import com.ipz.mba.entities.UserEntity;
import com.ipz.mba.exceptions.ClientDataRegistrationHasNullFieldsException;
import com.ipz.mba.exceptions.UserAlreadyExistsException;
import com.ipz.mba.models.ClientDataRegistration;
import com.ipz.mba.repositories.CustomerRepository;
import com.ipz.mba.repositories.RoleRepository;
import com.ipz.mba.repositories.UserRepository;
import com.ipz.mba.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    private final CustomerRepository customersRepository;
    private final UserRepository usersRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationServiceImpl(CustomerRepository customersRepository, UserRepository usersRepository, RoleRepository roleRepository) {
        this.customersRepository = customersRepository;
        this.usersRepository = usersRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    //@Transactional
    public void saveData(ClientDataRegistration clientData) throws Exception {

        // if client data contains null fields like a name, password, etc.
        if (ClientDataRegistration.hasNullFields(clientData)) {
            throw new ClientDataRegistrationHasNullFieldsException("Client-data has null fields");
        } else if (clientData.getPhoneNumber() == null && clientData.getIpn() == null) {
            throw new ClientDataRegistrationHasNullFieldsException("Phone-number and ipn are not specified.");
        }
        // if registration was by phone-number and this phone-number is present in DB
        else if (clientData.getPhoneNumber() != null &&
                usersRepository.findUserByPhoneNumber(clientData.getPhoneNumber()).isPresent()) {
            throw new UserAlreadyExistsException("User with such phone-number already exists.");
        }
        // if registration was by ipn-number and ipn-number is present in DB
        else if (clientData.getIpn() != null &&
                usersRepository.findUserByIpn(clientData.getIpn()).isPresent()) {
            throw new UserAlreadyExistsException("User with such ipn already exists.");
        }

        // password encoder
        clientData.setPassword(passwordEncoder.encode(clientData.getPassword()));

        // if all is ok, just save it
        UserEntity userEntity = ClientDataRegistration.getUserEntity(clientData);
        CustomerEntity customerEntity = ClientDataRegistration.getCustomerEntity(clientData);

        // add default role "user" to new customer
        Optional<RoleEntity> roleEntity = roleRepository.findByName("ROLE_USER");
        customerEntity.setRoles(Set.of(roleEntity.orElseThrow(() -> new Exception("Role was not found"))));

        customersRepository.save(customerEntity);
        userEntity.setId(customerEntity.getId());
        usersRepository.save(userEntity);
        customerEntity.setUserEntity(userEntity);
    }
}
