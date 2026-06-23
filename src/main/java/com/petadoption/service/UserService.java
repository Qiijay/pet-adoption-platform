package com.petadoption.service;

import com.petadoption.entity.User;

public interface UserService {
    User findByUsername(String username);
    User register(User user);
    boolean checkPassword(String rawPassword, String encodedPassword);
    String encodePassword(String rawPassword);
}