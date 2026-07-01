package com.merge.backend.scout.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Map;

public record Layer1SubmitRequest(
        @NotNull(message = "Responses must not be null")
        @Size(min = 8, max = 8, message = "All 8 questions must be answered")
        Map<String, String> responses
) {}
