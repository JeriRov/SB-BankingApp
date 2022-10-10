package com.ipz.mba.models;

import com.ipz.mba.entities.CustomerEntity;
import com.ipz.mba.entities.RoleEntity;
import com.ipz.mba.entities.UserEntity;
import lombok.*;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ClientDataRegistration {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String passportNumber;
    private String password;
    private List<RoleEntity> roles;


    public static boolean hasNullFields(ClientDataRegistration clientData) {
        return clientData.getFirstName() == null || clientData.getLastName() == null ||
                clientData.getPassword() == null || clientData.roles == null;
    }
    public static UserEntity getUserEntity(ClientDataRegistration data) {
        return new UserEntity(data.getPhoneNumber(), data.getPassportNumber(), data.getPassword());
    }

    public static CustomerEntity getCustomerEntity(ClientDataRegistration data) {
        return new CustomerEntity(data.getFirstName(), data.getLastName(), data.getRoles());
    }

}
