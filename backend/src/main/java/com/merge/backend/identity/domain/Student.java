package com.merge.backend.identity.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(name = "university_email", nullable = false, unique = true)
    private String universityEmail;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "current_stage", nullable = false)
    private String currentStage;

    @Column(name = "total_xp", nullable = false)
    private Integer totalXp = 0;

    @Column(name = "github_oauth_token_encrypted")
    private String githubOauthTokenEncrypted;

    @Column(name = "github_portfolio_repo")
    private String githubPortfolioRepo;

    @Column(name = "gemini_token_encrypted")
    private String geminiTokenEncrypted;

    public Student() {}

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

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getCurrentStage() { return currentStage; }
    public void setCurrentStage(String currentStage) { this.currentStage = currentStage; }

    public Integer getTotalXp() { return totalXp; }
    public void setTotalXp(Integer totalXp) { this.totalXp = totalXp; }

    public String getGithubOauthTokenEncrypted() { return githubOauthTokenEncrypted; }
    public void setGithubOauthTokenEncrypted(String githubOauthTokenEncrypted) {
        this.githubOauthTokenEncrypted = githubOauthTokenEncrypted;
    }

    public String getGithubPortfolioRepo() { return githubPortfolioRepo; }
    public void setGithubPortfolioRepo(String githubPortfolioRepo) {
        this.githubPortfolioRepo = githubPortfolioRepo;
    }

    public String getGeminiTokenEncrypted() { return geminiTokenEncrypted; }
    public void setGeminiTokenEncrypted(String geminiTokenEncrypted) {
        this.geminiTokenEncrypted = geminiTokenEncrypted;
    }
}
