package com.ipz.mba.services;

import com.ipz.mba.models.ClientDataRegistration;

public interface RegistrationService {
    void saveData(ClientDataRegistration clientData) throws Exception;
}
