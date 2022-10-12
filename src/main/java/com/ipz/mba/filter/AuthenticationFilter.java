package com.ipz.mba.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ipz.mba.entities.CustomerEntity;
import com.ipz.mba.entities.RoleEntity;
import com.ipz.mba.security.jwt.JWTProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        String phone = request.getParameter("phone_number");
        String passport = request.getParameter("passport_number");
        String password = request.getParameter("password");

        log.info("Passport or phone is: {}", passport == null ? phone : passport);
        log.info("Password is: {}", password);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(passport == null ? phone : passport, password);
        log.info("Token is: {}", authenticationToken);
        return this.authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authentication) throws IOException, ServletException {
        JWTProvider jwtProvider = new JWTProvider();
        CustomerEntity user = (CustomerEntity) authentication.getPrincipal(); ////Authentication returns the logged in user
        List<RoleEntity> roles = user.getRoles().stream().toList();


        String accessToken = jwtProvider.generateAccessToken(user.getUserEntity(), request, roles);

        String refreshToken = jwtProvider.generateRefreshToken(user.getUserEntity(), request);


        //To send accessToken and refreshToken in the body of the response (Body) in JSON format
        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }
}
