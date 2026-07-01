package com.merge.backend.scout.dto;

import java.time.Instant;
import java.util.Map;

public record Layer1SubmitResponse(
        Long assessmentId,
        Long studentId,
        Map<String, String> responses,
        Instant submittedAt
) {}
