package com.merge.backend.scout.controller;

import com.merge.backend.scout.dto.*;
import com.merge.backend.scout.service.AlreadySubmittedException;
import com.merge.backend.scout.service.NoLayer1SubmissionException;
import com.merge.backend.scout.service.ScoutService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/scout")
public class ScoutController {

    private final ScoutService scoutService;

    public ScoutController(ScoutService scoutService) {
        this.scoutService = scoutService;
    }

    /**
     * SC-01: GET /api/v1/scout/layer-1
     * Returns the 8 plain-language background intake questions.
     * Requires valid JWT (depends on ID-02).
     */
    @GetMapping("/layer-1")
    public ResponseEntity<Layer1QuestionsResponse> getLayer1Questions() {
        return ResponseEntity.ok(scoutService.getLayer1Questions());
    }

    /**
     * SC-01: POST /api/v1/scout/layer-1/submit
     * Accepts free-text answers and persists them to scout_assessments.layer1_responses (JSONB).
     * Requires valid JWT. One submission per student.
     */
    @PostMapping("/layer-1/submit")
    public ResponseEntity<Layer1SubmitResponse> submitLayer1(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody Layer1SubmitRequest request) {
        Layer1SubmitResponse response = scoutService.submitLayer1(userDetails.getUsername(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * SC-02: GET /api/v1/scout/layer-2
     * Returns the 4 conceptual problems. No coding — pure thinking and reasoning.
     */
    @GetMapping("/layer-2")
    public ResponseEntity<Layer2ProblemsResponse> getLayer2Problems() {
        return ResponseEntity.ok(scoutService.getLayer2Problems());
    }

    /**
     * SC-02: POST /api/v1/scout/layer-2/submit
     * Persists results to scout_assessments.layer2_results (JSONB).
     * Requires Layer 1 to already be submitted. One submission per student.
     */
    @PostMapping("/layer-2/submit")
    public ResponseEntity<Layer2SubmitResponse> submitLayer2(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody Layer2SubmitRequest request) {
        Layer2SubmitResponse response = scoutService.submitLayer2(userDetails.getUsername(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * SC-03: GET /api/v1/scout/layer-3
     * Returns the baseline coding task if the student indicated prior experience in Layer 1 (q4).
     * Returns {@code eligible: false} with no task fields if no prior experience — frontend skips.
     * Requires Layer 1 and Layer 2 to have been completed first.
     */
    @GetMapping("/layer-3")
    public ResponseEntity<Layer3TaskResponse> getLayer3Task(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(scoutService.getLayer3Task(userDetails.getUsername()));
    }

    /**
     * SC-03: POST /api/v1/scout/layer-3/submit
     * Persists the student's code to scout_assessments.layer3_code.
     * If the student has no prior coding experience the request is accepted but skipped
     * ({@code skipped: true}) — layer3_code remains null.
     */
    @PostMapping("/layer-3/submit")
    public ResponseEntity<Layer3SubmitResponse> submitLayer3(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody Layer3SubmitRequest request) {
        Layer3SubmitResponse response = scoutService.submitLayer3(userDetails.getUsername(), request);
        HttpStatus status = response.skipped() ? HttpStatus.OK : HttpStatus.CREATED;
        return ResponseEntity.status(status).body(response);
    }

    /**
     * SC-05: POST /api/v1/scout/complete
     * Triggers SC-04 profile generation from the completed Scout assessment, sets
     * student.currentStage to CADET, and returns a profile summary + redirect target.
     * Closes the Scout stage permanently — cannot be called again once completed.
     * Requires Layer 1, Layer 2, and (if applicable) Layer 3 to already be submitted.
     */
    @PostMapping("/complete")
    public ResponseEntity<ScoutCompleteResponse> completeScout(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(scoutService.completeScout(userDetails.getUsername()));
    }

    @ExceptionHandler(AlreadySubmittedException.class)
    public ResponseEntity<?> handleAlreadySubmitted(AlreadySubmittedException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(NoLayer1SubmissionException.class)
    public ResponseEntity<?> handleNoLayer1(NoLayer1SubmissionException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(Map.of("error", ex.getMessage()));
    }
}
