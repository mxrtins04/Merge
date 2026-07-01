package com.merge.backend.scout.dto;

import java.time.Instant;

/**
 * Response for POST /scout/layer-3/submit.
 * {@code skipped} is true when the student had no prior coding experience — code was not stored.
 */
public record Layer3SubmitResponse(
        Long assessmentId,
        Long studentId,
        boolean skipped,
        Instant submittedAt
) {}
