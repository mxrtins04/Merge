/**
 * Assessment Module
 * 
 * Responsibilities:
 * - Drill management (retrieval and delivery of Drill 1 and Drill 2).
 * - Code reading gates (forcing code comprehension review before edits).
 * - Submissions execution (interfacing with Judge0 container for testing).
 * - Comprehension checks orchestration (generating post-test questions and enforcing the server-side timer).
 * - Build evaluation (running five gate builds: hidden test cases, TDD test suite, clean code score, build comprehension check, and SFIA alignment).
 * 
 * Key Entities:
 * - {@code Drill}: Custom PRD + coding task for concepts.
 * - {@code Submission}: Student's submitted code, test suite, and answers.
 * - {@code ComprehensionCheck}: Dynamic AI-generated question sheet and server deadline timer.
 * - {@code Build}: Multi-gate capstone project for each stage.
 * - {@code BuildSubmission}: Student's submitted build artifacts.
 * 
 * Communication Rules:
 * - External modules must access assessment actions via {@code AssessmentService}.
 */
package com.merge.backend.assessment;
