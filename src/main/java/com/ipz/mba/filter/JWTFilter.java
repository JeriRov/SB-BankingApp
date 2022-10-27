package com.ipz.mba.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ipz.mba.security.jwt.JWTUtil;
import com.ipz.mba.security.models.CustomerDetails;
import com.ipz.mba.security.services.CustomerDetailsService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@Component
@Slf4j
public class JWTFilter extends OncePerRequestFilter {

    private static final String BEARER = "Bearer";
    private final JWTUtil jwtUtil;
    private final CustomerDetailsService customerDetailsService;

    @Autowired
    public JWTFilter(JWTUtil jwtUtil, CustomerDetailsService customerDetailsService) {
        this.jwtUtil = jwtUtil;
        this.customerDetailsService = customerDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith(BEARER)) {
            String jwtToken = authHeader.substring(BEARER.length()).trim();
            log.info("jwt is {}", jwtToken);
            try {
                // here we`ll have phoneNumber or ipn
                String data = jwtUtil.validateToken(jwtToken);

                // search customer by phoneNumber at first, and then by ipn
                UserDetails userDetailsSearchedByPhone = customerDetailsService.loadUserByUsername(data);
                UserDetails userDetailsSearchedByIpn = customerDetailsService.loadUserByUsername(data);

                // define customer
                CustomerDetails customerDetails = (CustomerDetails) (userDetailsSearchedByPhone != null ?
                        userDetailsSearchedByPhone : userDetailsSearchedByIpn);

                var authToken = new UsernamePasswordAuthenticationToken(
                        customerDetails,
                        customerDetails.getPassword(),
                        customerDetails.getAuthorities()
                );

                if (SecurityContextHolder.getContext().getAuthentication() == null) {
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }

            } catch (JWTVerificationException ex) {
                createResponse(response, ex, jwtToken);
            }
        }
        filterChain.doFilter(request, response);
    }

    private void createResponse(HttpServletResponse response, Exception ex, String jwtToken) throws IOException {
        log.error("jwt JWTVerificationException");
        log.error("jwt {}", jwtToken);
        response.setHeader("error", "Invalid JWT");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        var error = new HashMap<>();
        error.put("errorMessage", ex.getMessage());
        new ObjectMapper().writeValue(response.getOutputStream(), error);
    }
}