package com.ipz.mba.models;

import com.ipz.mba.entities.CustomerEntity;
import com.ipz.mba.entities.UserEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ClientDataRegistration {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String passportNumber;
    private String ipn;
    private String password;

    public static boolean hasNullFields(ClientDataRegistration clientData) {
        return clientData.getFirstName() == null || clientData.getLastName() == null || clientData.getPassword() == null ||
                clientData.getPhoneNumber() == null || clientData.getIpn() == null || clientData.getPassportNumber() == null;
    }

    public static UserEntity getUserEntity(ClientDataRegistration data) {
        return new UserEntity(data.getPhoneNumber(), data.getIpn(), data.getPassportNumber(), null, data.getPassword());
    }

    public static CustomerEntity getCustomerEntity(ClientDataRegistration data) {
        return new CustomerEntity(data.getFirstName(), data.getLastName(), new HashSet<>(), new HashSet<>());
    }
}
