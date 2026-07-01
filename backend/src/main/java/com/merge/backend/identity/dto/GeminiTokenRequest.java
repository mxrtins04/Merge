package com.merge.backend.identity.dto;

import jakarta.validation.constraints.NotBlank;

public record GeminiTokenRequest(
        @NotBlank(message = "Token must not be blank")
        String token
) {}
