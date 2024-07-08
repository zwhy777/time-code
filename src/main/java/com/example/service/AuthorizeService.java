package com.example.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Stack;

public interface AuthorizeService  extends UserDetailsService {
    String sendValidateEmail(String email,String sessionId);
    String validateAndRegister(String username,String password,String email,String code,String sessionId);
}
