/**
 * AI Module
 * 
 * Responsibilities:
 * - Handling all calls to Google Gemini API.
 * - Retrieving context embeddings from pgvector.
 * - Prompt orchestration across 9 specialist roles (Curriculum Writer, Drill Generator, Comprehension Generator, Clean Code Reviewer, etc.).
 * - Generating audio scripts.
 * 
 * Key Entities:
 * - {@code AIOrchestrationService}: Single gateway wrapper for LLM requests.
 * - {@code AudioRecord}: Generated audio scripts and file locations.
 */
package com.merge.backend.ai;
