package com.merge.backend.scout.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Map;

public record Layer2SubmitRequest(
        @NotNull(message = "Results must not be null")
        @Size(min = 4, max = 4, message = "All 4 conceptual problems must be answered")
        Map<String, String> results
) {}
