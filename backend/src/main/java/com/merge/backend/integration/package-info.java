/**
 * Integration Module
 * 
 * Responsibilities:
 * - GitHub connection & portfolio auto-commit integration (GitHub API + Octokit).
 * - Judge0 compilation and execution sandbox integration.
 * - Cloudflare R2 file storage integration (for CV files and recap audio).
 * - Intercom message notifications for disengagement reach-out.
 * 
 * Services:
 * - {@code GitHubService}: Handles OAuth and auto-commit pushes.
 * - {@code Judge0Service}: Sends code submissions and polls execution status.
 * - {@code CloudflareR2Service}: Manages binary files upload/download.
 */
package com.merge.backend.integration;
