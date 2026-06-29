/**
 * Progression Module
 * 
 * Responsibilities:
 * - XP atomic transactions (enforcing SELECT FOR UPDATE row locking for balance concurrency).
 * - XP capping rules enforcement per stage.
 * - Attempt-based XP decay (calculating 100%, 75%, 50%, 25% decay rates).
 * - Stage promotion verification (validating both gates: total XP threshold and Build distinction pass score).
 * 
 * Key Entities:
 * - {@code XPRecord}: Ledger entry of student XP awards mapped to SFIA dimensions.
 * - {@code XPCap}: Stage-level rules setting caps per activity type (e.g. max 50 XP on resources).
 * - {@code StagePromotion}: Record of successful promotions from stage to stage.
 * 
 * Communication Rules:
 * - XP changes should be made solely through {@code ProgressionService.awardXP()}.
 */
package com.merge.backend.progression;
