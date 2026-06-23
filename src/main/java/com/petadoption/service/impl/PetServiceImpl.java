package com.petadoption.service.impl;

import com.petadoption.entity.Pet;
import com.petadoption.mapper.PetMapper;
import com.petadoption.service.PetService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetServiceImpl implements PetService {

    private final PetMapper petMapper;

    public PetServiceImpl(PetMapper petMapper) {
        this.petMapper = petMapper;
    }

    @Override
    public Pet addPet(Pet pet) {
        // 默认状态为 AVAILABLE
        if (pet.getStatus() == null) {
            pet.setStatus("AVAILABLE");
        }
        petMapper.insert(pet);
        return pet;
    }

    @Override
    public Pet getPetById(Long id) {
        return petMapper.findById(id);
    }

    @Override
    public List<Pet> getAllPets() {
        return petMapper.findAll();
    }

    @Override
    public boolean updatePetStatus(Long id, String status) {
        return petMapper.updateStatus(id, status) > 0;
    }
}