package com.merge.backend.scout.dto;

/**
 * Response for GET /scout/layer-3.
 * When {@code eligible} is false all other fields are null — the student reported no prior
 * coding experience in Layer 1 and Layer 3 should be skipped in the frontend.
 */
public record Layer3TaskResponse(
        boolean eligible,
        String taskId,
        String title,
        String description,
        String exampleInput,
        String exampleOutput
) {
    public static Layer3TaskResponse notEligible() {
        return new Layer3TaskResponse(false, null, null, null, null, null);
    }
}
