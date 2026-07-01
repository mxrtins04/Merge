package com.merge.backend.identity.dto;

public class AuthResponse {

    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";
    private StudentResponse student;

    public AuthResponse() {}

    public AuthResponse(String accessToken, String refreshToken, StudentResponse student) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.student = student;
    }

    public String getAccessToken() { return accessToken; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }

    public String getRefreshToken() { return refreshToken; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }

    public String getTokenType() { return tokenType; }
    public void setTokenType(String tokenType) { this.tokenType = tokenType; }

    public StudentResponse getStudent() { return student; }
    public void setStudent(StudentResponse student) { this.student = student; }
}
