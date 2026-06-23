package com.petadoption.service.impl;

import com.petadoption.entity.User;
import com.petadoption.mapper.UserMapper;
import com.petadoption.service.UserService;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public User register(User user) {
        String encoded = encodePassword(user.getPassword());
        System.out.println("注册加密结果: " + encoded);
        user.setPassword(encoded);
        user.setStatus(1);
        userMapper.insert(user);
        return user;
    }

    @Override
    public boolean checkPassword(String rawPassword, String encodedPassword) {
        String encoded = encodePassword(rawPassword);
        System.out.println("输入的密码加密后: " + encoded);
        System.out.println("数据库中的密码: " + encodedPassword);
        boolean result = encoded.equals(encodedPassword);
        System.out.println("是否匹配: " + result);
        return result;
    }

    @Override
    public String encodePassword(String rawPassword) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(rawPassword.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("密码加密失败", e);
        }
    }
}