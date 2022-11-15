package com.ipz.mba.controllers;

import com.ipz.mba.entities.CustomerEntity;
import com.ipz.mba.models.UserInfo;
import com.ipz.mba.security.models.CustomerDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
public class UsersController {

    @GetMapping("/info")
    public UserInfo getUserInfo() {
        log.info("UsersController: getUserInfo()");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomerEntity customer = ((CustomerDetails) auth.getPrincipal()).getCustomer();

        return new UserInfo(customer.getFirstName(), customer.getLastName());
    }
}
