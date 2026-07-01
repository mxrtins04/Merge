package com.merge.backend.identity.dto;

import com.merge.backend.identity.domain.Student;

/**
 * Safe outbound representation of a student.
 * Never exposes passwordHash, githubOauthTokenEncrypted, or geminiTokenEncrypted.
 */
public class StudentResponse {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String universityEmail;
    private String currentStage;
    private Integer totalXp;
    private String githubPortfolioRepo;
    private boolean githubConnected;

    public static StudentResponse from(Student student) {
        StudentResponse dto = new StudentResponse();
        dto.id = student.getId();
        dto.name = student.getName();
        dto.email = student.getEmail();
        dto.phone = student.getPhone();
        dto.universityEmail = student.getUniversityEmail();
        dto.currentStage = student.getCurrentStage();
        dto.totalXp = student.getTotalXp();
        dto.githubPortfolioRepo = student.getGithubPortfolioRepo();
        dto.githubConnected = student.getGithubOauthTokenEncrypted() != null;
        return dto;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getUniversityEmail() { return universityEmail; }
    public void setUniversityEmail(String universityEmail) { this.universityEmail = universityEmail; }

    public String getCurrentStage() { return currentStage; }
    public void setCurrentStage(String currentStage) { this.currentStage = currentStage; }

    public Integer getTotalXp() { return totalXp; }
    public void setTotalXp(Integer totalXp) { this.totalXp = totalXp; }

    public String getGithubPortfolioRepo() { return githubPortfolioRepo; }
    public void setGithubPortfolioRepo(String githubPortfolioRepo) {
        this.githubPortfolioRepo = githubPortfolioRepo;
    }

    public boolean isGithubConnected() { return githubConnected; }
    public void setGithubConnected(boolean githubConnected) { this.githubConnected = githubConnected; }
}
