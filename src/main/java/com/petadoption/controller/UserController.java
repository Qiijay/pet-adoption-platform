package com.petadoption.controller;

import com.petadoption.common.Result;
import com.petadoption.entity.User;
import com.petadoption.mapper.UserMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserMapper userMapper;

    public UserController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @GetMapping("/list")
    public Result<List<User>> list() {
        List<User> users = userMapper.findAll();
        return Result.success(users);
    }
}