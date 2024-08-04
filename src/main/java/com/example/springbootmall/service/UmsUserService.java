package com.example.springbootmall.service;

import com.example.springbootmall.component.CommonResult;
import com.example.springbootmall.model.UmsUser;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface UmsUserService {
    int register(UmsUser user);
    int update(Long userId, UmsUser user);
    int delete(Long userId);
    UmsUser getUserById(Long userId);
    UmsUser getUserByUsername(String username);
    List<UmsUser> getAllUser();
    int login(String username, String password);
    String generateAuthCode(String telephone, HttpSession session);
    int verifyAuthCode(String telephone, String authCode, HttpSession session);
}
