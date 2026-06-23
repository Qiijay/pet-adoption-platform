package com.petadoption.controller;

import com.petadoption.common.Result;
import com.petadoption.dto.request.AddPetRequest;
import com.petadoption.entity.Pet;
import com.petadoption.service.PetService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    /**
     * 发布宠物（救助站管理员）
     */
    @PostMapping("/add")
    public Result<Pet> addPet(@RequestBody AddPetRequest request) {
        Pet pet = new Pet();
        pet.setName(request.getName());
        pet.setSpecies(request.getSpecies());
        pet.setBreed(request.getBreed());
        pet.setAge(request.getAge());
        pet.setGender(request.getGender());
        pet.setWeight(request.getWeight());
        pet.setColor(request.getColor());
        pet.setHealthStatus(request.getHealthStatus());
        pet.setDescription(request.getDescription());
        pet.setImageUrl(request.getImageUrl());
        pet.setShelterId(request.getShelterId());

        Pet saved = petService.addPet(pet);
        return Result.success(saved);
    }

    /**
     * 获取所有宠物列表
     */
    @GetMapping("/list")
    public Result<List<Pet>> list() {
        return Result.success(petService.getAllPets());
    }

    /**
     * 获取宠物详情
     */
    @GetMapping("/{id}")
    public Result<Pet> detail(@PathVariable Long id) {
        Pet pet = petService.getPetById(id);
        if (pet == null) {
            return Result.error("宠物不存在");
        }
        return Result.success(pet);
    }
}