package com.petadoption.mapper;

import com.petadoption.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM users WHERE username = #{username}")
    User findByUsername(String username);

    @Select("SELECT * FROM users WHERE id = #{id}")
    User findById(Long id);

    @Insert("INSERT INTO users(username, password, role, phone, email, real_name) " +
            "VALUES(#{username}, #{password}, #{role}, #{phone}, #{email}, #{realName})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);

    @Select("SELECT * FROM users")
    List<User> findAll();
}