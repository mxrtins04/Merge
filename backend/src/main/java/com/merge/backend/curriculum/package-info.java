/**
 * Curriculum Module
 * 
 * Responsibilities:
 * - Stage and concept management (sequence mapping, unlocking gates).
 * - Written explanations management (AI-generated curriculum pages).
 * - Supplementary learning resources delivery.
 * - Syntax micro-exercises (Cadet stage syntax drills).
 * 
 * Key Entities:
 * - {@code Stage}: Definition of formation ranks (SCOUT, CADET, ENGINEER, ARCHITECT, PRINCIPAL).
 * - {@code Concept}: Core engineering topics (12 concepts in Cadet stage).
 * - {@code ConceptContent}: Unique AI-generated, personalised explanations per student.
 * - {@code LearningResource}: Supplementary material (specific books, timestamps, etc.).
 * - {@code SyntaxExercise}: Cadet stage interactive micro-drills.
 * - {@code MergeLabsCompany}: Context companies for Drills.
 * 
 * Communication Rules:
 * - External modules must access curriculum data via {@code CurriculumService} interface.
 * - Direct repository access from outside the package is prohibited.
 */
package com.merge.backend.curriculum;
