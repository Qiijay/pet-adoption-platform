package com.petadoption.service;

import com.petadoption.entity.Pet;
import java.util.List;

public interface PetService {
    Pet addPet(Pet pet);
    Pet getPetById(Long id);
    List<Pet> getAllPets();
    boolean updatePetStatus(Long id, String status);
}