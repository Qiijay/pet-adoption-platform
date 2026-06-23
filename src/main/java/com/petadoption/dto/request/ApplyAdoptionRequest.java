package com.petadoption.dto.request;

public class ApplyAdoptionRequest {
    private Long petId;
    private String homeEnvironment;
    private String experience;
    private String reason;

    public Long getPetId() { return petId; }
    public void setPetId(Long petId) { this.petId = petId; }

    public String getHomeEnvironment() { return homeEnvironment; }
    public void setHomeEnvironment(String homeEnvironment) { this.homeEnvironment = homeEnvironment; }

    public String getExperience() { return experience; }
    public void setExperience(String experience) { this.experience = experience; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}