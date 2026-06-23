package com.petadoption.controller;

import com.petadoption.common.Result;
import com.petadoption.common.utils.JwtUtil;
import com.petadoption.dto.request.LoginRequest;
import com.petadoption.dto.request.RegisterRequest;
import com.petadoption.entity.User;
import com.petadoption.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public Result<Map<String, String>> register(@RequestBody RegisterRequest request) {
        if (userService.findByUsername(request.getUsername()) != null) {
            return Result.error("用户名已存在");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setRealName(request.getRealName());
        user.setRole("USER");

        userService.register(user);

        Map<String, String> data = new HashMap<>();
        data.put("message", "注册成功");
        return Result.success(data);
    }

    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody LoginRequest request) {
        User user = userService.findByUsername(request.getUsername());
        if (user == null) {
            return Result.error("用户名不存在");
        }

        // 明文比对密码
        if (!userService.checkPassword(request.getPassword(), user.getPassword())) {
            return Result.error("密码错误");
        }

        if (user.getStatus() == 0) {
            return Result.error("账户已被禁用");
        }

        // 生成Token
        String token = jwtUtil.generateToken(
                user.getId(),
                user.getUsername() != null ? user.getUsername() : "unknown",
                user.getRole() != null ? user.getRole() : "USER"
        );

        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("userId", user.getId());
        data.put("username", user.getUsername());
        data.put("role", user.getRole());

        return Result.success(data);
    }
}