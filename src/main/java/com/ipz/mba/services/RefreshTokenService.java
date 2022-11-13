package com.ipz.mba.services;

import com.ipz.mba.entities.UserEntity;

public interface RefreshTokenService {
    String switchRefreshToken(String number);
    UserEntity isUserPresentWithRefreshToken(String phoneNumber, String refreshToken);
    void updateRefreshToken(UserEntity userEntity, String refreshToken);
}
