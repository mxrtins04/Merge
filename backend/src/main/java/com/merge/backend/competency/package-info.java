/**
 * Competency Module
 * 
 * Responsibilities:
 * - Evidence logging (mapping events to competency tags).
 * - Competency dimension tracking.
 * - Assembly of a student's holistic Engineering Identity profile.
 * 
 * Key Entities:
 * - {@code Competency}: The 8 core dimensions (Problem Solving, Software Design, Code Quality, Testing, Systems Thinking, Collaboration, Ownership, Growth).
 * - {@code CompetencyEvidence}: Individual data points derived from drills, checks, builds.
 * - {@code StudentCompetency}: Aggregate status of a student's rating per dimension.
 * - {@code EngineeringIdentity}: Final public profile representing the student's capability.
 */
package com.merge.backend.competency;
