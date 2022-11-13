package com.ipz.mba.services.impl;

import com.ipz.mba.entities.UserEntity;
import com.ipz.mba.repositories.UserRepository;
import com.ipz.mba.security.jwt.JWTUtil;
import com.ipz.mba.services.RefreshTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;

    @Autowired
    public RefreshTokenServiceImpl(UserRepository userRepository, JWTUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public String switchRefreshToken(String number) {
        log.info("RefreshTokenServiceImpl: switchRefreshToken(number)");
        // find user
        var optionalUser = userRepository.findUserByIpn(number);
        UserEntity user = optionalUser.orElseGet(() -> userRepository.findUserByPhoneNumber(number).get());

        String newRefreshToken = jwtUtil.generateRefreshToken(user.getPhoneNumber(), user.getIpn());

        // update RT
        updateRefreshToken(user, newRefreshToken);

        return newRefreshToken;
    }

    @Override
    public UserEntity isUserPresentWithRefreshToken(String phoneNumber, String refreshToken) {
        return userRepository.findUserByPhoneNumberAndRefreshToken(phoneNumber, refreshToken).orElse(null);
    }

    @Override
    public void updateRefreshToken(UserEntity userEntity, String refreshToken) {
        log.info("RefreshTokenServiceImpl: updateRefreshToken()");
        userEntity.setRefreshToken(refreshToken);
        userRepository.save(userEntity);
    }
}