package com.petadoption.service.impl;

import com.petadoption.entity.AdoptionApplication;
import com.petadoption.mapper.AdoptionMapper;
import com.petadoption.service.AdoptionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdoptionServiceImpl implements AdoptionService {

    private final AdoptionMapper adoptionMapper;

    public AdoptionServiceImpl(AdoptionMapper adoptionMapper) {
        this.adoptionMapper = adoptionMapper;
    }

    @Override
    public AdoptionApplication apply(AdoptionApplication application) {
        application.setStatus("PENDING");
        adoptionMapper.insert(application);
        return application;
    }

    @Override
    public AdoptionApplication getById(Long id) {
        return adoptionMapper.findById(id);
    }

    @Override
    public List<AdoptionApplication> getByApplicant(Long applicantId) {
        return adoptionMapper.findByApplicantId(applicantId);
    }

    @Override
    public List<AdoptionApplication> getByPet(Long petId) {
        return adoptionMapper.findByPetId(petId);
    }
}