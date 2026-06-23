package com.petadoption.mapper;

import com.petadoption.entity.Pet;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PetMapper {

    @Insert("INSERT INTO pets(name, species, breed, age, gender, weight, color, health_status, description, image_url, status, shelter_id) " +
            "VALUES(#{name}, #{species}, #{breed}, #{age}, #{gender}, #{weight}, #{color}, #{healthStatus}, #{description}, #{imageUrl}, #{status}, #{shelterId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Pet pet);

    @Select("SELECT * FROM pets WHERE id = #{id}")
    Pet findById(Long id);

    @Select("SELECT * FROM pets ORDER BY created_at DESC")
    List<Pet> findAll();

    @Update("UPDATE pets SET status = #{status} WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}