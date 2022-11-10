package com.ipz.mba.models;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ClientDataLogin {
    private String ipn;
    private String phoneNumber;
    private String password;

    public boolean isPhone() {
        return phoneNumber != null;
    }
}
