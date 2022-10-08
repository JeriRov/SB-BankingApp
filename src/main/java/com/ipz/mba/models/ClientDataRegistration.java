package com.ipz.mba.models;

import com.ipz.mba.entities.CustomerEntity;
import com.ipz.mba.entities.UserEntity;
import lombok.*;

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
    private Boolean premium;


    public static boolean hasNullFields(ClientDataRegistration clientData) {
        return clientData.getFirstName() == null || clientData.getLastName() == null ||
                clientData.getPassword() == null || clientData.getPremium() == null;
    }
    public static UserEntity getUserEntity(ClientDataRegistration data) {
        return new UserEntity(data.getPhoneNumber(), data.getPassportNumber(), data.getPassword());
    }

    public static CustomerEntity getCustomerEntity(ClientDataRegistration data) {
        return new CustomerEntity(data.getFirstName(), data.getLastName(), data.getPremium());
    }

}
