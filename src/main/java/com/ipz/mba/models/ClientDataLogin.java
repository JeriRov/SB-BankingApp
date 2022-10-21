package com.ipz.mba.models;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ClientDataLogin {
    private String ipn;
    private String phoneNumber;
    private String password;

    public boolean isPhone(){
        return phoneNumber != null;
    }
}
