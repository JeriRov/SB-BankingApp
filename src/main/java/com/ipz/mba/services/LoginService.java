package com.ipz.mba.services;

import com.ipz.mba.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private final UserRepository usersRepository;

    @Autowired
    public LoginService(UserRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public String getPasswordByPhone(String number){
        return usersRepository.findUserByPhoneNumber(number).get().getPassword();
    }

    public String getPasswordByIpn(String ipn){
        return usersRepository.findUserByIpn(ipn).get().getPassword();
    }

}
