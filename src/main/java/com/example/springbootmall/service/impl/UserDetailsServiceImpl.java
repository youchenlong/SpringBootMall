package com.example.springbootmall.service.impl;

import com.example.springbootmall.domain.AdminUserDetails;
import com.example.springbootmall.model.UmsUser;
import com.example.springbootmall.service.UmsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UmsUserService umsUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UmsUser user = umsUserService.getUserByUsername(username);
        return new AdminUserDetails(user);
    }
}
