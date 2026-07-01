package com.merge.backend.scout.dto;

import java.time.Instant;
import java.util.Map;

public record Layer2SubmitResponse(
        Long assessmentId,
        Long studentId,
        Map<String, String> results,
        Instant submittedAt
) {}
