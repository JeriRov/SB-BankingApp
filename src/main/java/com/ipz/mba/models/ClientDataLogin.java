package com.ipz.mba.models;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ClientDataLogin {
    private String ipn, phoneNumber, password;

    public boolean isPhone(){
        return phoneNumber != null;
    }
}
