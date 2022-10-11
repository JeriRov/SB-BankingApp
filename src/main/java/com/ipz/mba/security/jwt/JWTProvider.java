package com.ipz.mba.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ipz.mba.entities.RoleEntity;
import com.ipz.mba.entities.UserEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;


@Component
public class JWTProvider {

    private final String SECRET_KEY = "secret";

    public Algorithm getAlgorithm(){
        return Algorithm.HMAC256(SECRET_KEY.getBytes());
    }

    public String generateAccessToken(UserEntity user, HttpServletRequest request, List<RoleEntity> roles){
        return JWT.create()
                .withSubject(user.getPhoneNumber() == null ? user.getPassportNumber() : user.getPhoneNumber())
                .withExpiresAt(new Date(System.currentTimeMillis() + 600 * 1000)) //Current time (milliseconds) + 600000 milliseconds (10 minutes * 60 seconds * 1000 milliseconds)
                .withIssuer(request.getRequestURL().toString())//The name of the company or token author, in our case the request url
                .withClaim("roles", roles.toString()) //All roles of this user
                .sign(getAlgorithm());
    }

    public String generateRefreshToken(UserEntity user, HttpServletRequest request){
        return JWT.create()
                .withSubject(user.getPhoneNumber() == null ? user.getPassportNumber() : user.getPhoneNumber())
                .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000)) //We give more time for RefreshToken than AccessToken:
                .withIssuer(request.getRequestURL().toString())
                .sign(getAlgorithm());
    }

}
