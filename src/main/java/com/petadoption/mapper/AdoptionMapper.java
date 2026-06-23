package com.petadoption.mapper;

import com.petadoption.entity.AdoptionApplication;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdoptionMapper {

    @Insert("INSERT INTO adoption_applications(pet_id, applicant_id, home_environment, experience, reason, status) " +
            "VALUES(#{petId}, #{applicantId}, #{homeEnvironment}, #{experience}, #{reason}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(AdoptionApplication application);

    @Select("SELECT * FROM adoption_applications WHERE id = #{id}")
    AdoptionApplication findById(Long id);

    @Select("SELECT * FROM adoption_applications WHERE applicant_id = #{applicantId} ORDER BY created_at DESC")
    List<AdoptionApplication> findByApplicantId(Long applicantId);

    @Select("SELECT * FROM adoption_applications WHERE pet_id = #{petId}")
    List<AdoptionApplication> findByPetId(Long petId);
}