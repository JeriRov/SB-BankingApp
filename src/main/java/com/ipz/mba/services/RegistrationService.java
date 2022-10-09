package com.ipz.mba.services;

import com.ipz.mba.entities.CustomerEntity;
import com.ipz.mba.entities.UserEntity;
import com.ipz.mba.exceptions.ClientDataRegistrationHasNullFieldsException;
import com.ipz.mba.exceptions.UserAlreadyExistsException;
import com.ipz.mba.models.ClientDataRegistration;
import com.ipz.mba.repositories.CustomerRepository;
import com.ipz.mba.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    private final CustomerRepository customersRepository;
    private final UserRepository usersRepository;

    @Autowired
    public RegistrationService(CustomerRepository customersRepository, UserRepository usersRepository) {
        this.customersRepository = customersRepository;
        this.usersRepository = usersRepository;
    }

    public void saveData(ClientDataRegistration clientData) throws UserAlreadyExistsException, ClientDataRegistrationHasNullFieldsException {

        // if client data contains null fields like a name, password, etc.
        if (ClientDataRegistration.hasNullFields(clientData)) {
            throw new ClientDataRegistrationHasNullFieldsException("Client-data has null fields");
        }
        else if (clientData.getPhoneNumber() == null && clientData.getPassportNumber() == null) {
            throw new ClientDataRegistrationHasNullFieldsException("Phone-number and passport-number are not specified.");
        }
        // if registration was by phone-number and this phone-number is present in DB
        else if (clientData.getPhoneNumber() != null &&
                usersRepository.findUserByPhoneNumber(clientData.getPhoneNumber()) != null) {
            throw new UserAlreadyExistsException("User with such phone-number already exists.");
        }
        // if registration was by passport-number and passport-number is present in DB
        else if (clientData.getPassportNumber() != null &&
                usersRepository.findUserByPassportNumber(clientData.getPassportNumber()) != null){
            throw new UserAlreadyExistsException("User with such passport-number already exists.");
        }

        // if all is ok, just save it
        UserEntity userEntity = ClientDataRegistration.getUserEntity(clientData);
        CustomerEntity customerEntity = ClientDataRegistration.getCustomerEntity(clientData);

        customersRepository.save(customerEntity);
        userEntity.setId(customerEntity.getId());
        usersRepository.save(userEntity);
        customerEntity.setUserEntity(userEntity);
    }
}
