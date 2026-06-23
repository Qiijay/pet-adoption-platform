package com.petadoption.service;

import com.petadoption.entity.AdoptionApplication;
import java.util.List;

public interface AdoptionService {
    AdoptionApplication apply(AdoptionApplication application);
    AdoptionApplication getById(Long id);
    List<AdoptionApplication> getByApplicant(Long applicantId);
    List<AdoptionApplication> getByPet(Long petId);
}