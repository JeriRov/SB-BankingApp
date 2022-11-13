package com.ipz.mba.services;

public interface RefreshTokenService {
    String switchRefreshToken(String number);
    boolean isUserPresentWithRefreshToken(String phoneNumber, String refreshToken);
}
