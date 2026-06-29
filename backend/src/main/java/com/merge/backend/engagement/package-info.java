/**
 * Engagement Module
 * 
 * Responsibilities:
 * - Session state tracking (session startup with mood value).
 * - Weekly Momentum Score calculation (DEPLOYING, BUILDING, COMPILING, BLOCKED, OFFLINE).
 * - Season creation and rankings freeze at semester boundaries.
 * - Season badges award system.
 * - Disengagement monitoring (detecting blocked states and triggering reach-out via Intercom).
 * 
 * Key Entities:
 * - {@code Session}: Record of a student study session.
 * - {@code WeeklyMomentum}: Log of student momentum scores updated every Monday.
 * - {@code Season}: Definition of a academic/university semester.
 * - {@code SeasonBadge}: GOLD or SILVER badge awards mapped to students.
 */
package com.merge.backend.engagement;
