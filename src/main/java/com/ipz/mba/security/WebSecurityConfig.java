package com.ipz.mba.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration @EnableWebSecurity @RequiredArgsConstructor
public class WebSecurityConfig {

    //defines which URL paths should be secured
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .antMatchers("/register", "/login/phone/{phone}/{password}",
                                "/login/passport/{passport}/{password}").permitAll()
                        .antMatchers("/adminPage").hasRole("ADMIN")
                        .anyRequest().authenticated()
                );
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        //Password is encoded, so we don't need any Encoder here
        UserDetails user = User.builder()
                        .username("user")
                        .password("password")
                        .roles("USER")
                        .build();
        return new InMemoryUserDetailsManager(user);
    }

}
