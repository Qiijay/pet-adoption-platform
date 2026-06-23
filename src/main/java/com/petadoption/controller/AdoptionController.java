package com.petadoption.controller;

import com.petadoption.common.Result;
import com.petadoption.dto.request.ApplyAdoptionRequest;
import com.petadoption.entity.AdoptionApplication;
import com.petadoption.entity.Pet;
import com.petadoption.service.AdoptionService;
import com.petadoption.service.PetService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/adoptions")
public class AdoptionController {

    private final AdoptionService adoptionService;
    private final PetService petService;

    public AdoptionController(AdoptionService adoptionService, PetService petService) {
        this.adoptionService = adoptionService;
        this.petService = petService;
    }

    /**
     * 提交领养申请
     */
    @PostMapping("/apply")
    public Result<AdoptionApplication> apply(@RequestBody ApplyAdoptionRequest request) {
        // 检查宠物是否存在
        Pet pet = petService.getPetById(request.getPetId());
        if (pet == null) {
            return Result.error("宠物不存在");
        }

        // 检查宠物是否可领养
        if (!"AVAILABLE".equals(pet.getStatus())) {
            return Result.error("该宠物已被领养或预留");
        }

        // 创建申请（申请人ID暂时写死为1，后续从Token获取）
        AdoptionApplication application = new AdoptionApplication();
        application.setPetId(request.getPetId());
        application.setApplicantId(1L); // 临时写死
        application.setHomeEnvironment(request.getHomeEnvironment());
        application.setExperience(request.getExperience());
        application.setReason(request.getReason());

        AdoptionApplication saved = adoptionService.apply(application);

        // 更新宠物状态为 RESERVED
        petService.updatePetStatus(request.getPetId(), "RESERVED");

        return Result.success(saved);
    }

    /**
     * 查询用户的申请记录
     */
    @GetMapping("/my")
    public Result<List<AdoptionApplication>> myApplications() {
        Long userId = 1L; // 临时写死
        return Result.success(adoptionService.getByApplicant(userId));
    }

    /**
     * 查询某个宠物的所有申请
     */
    @GetMapping("/pet/{petId}")
    public Result<List<AdoptionApplication>> petApplications(@PathVariable Long petId) {
        return Result.success(adoptionService.getByPet(petId));
    }
}