package com.merge.backend.infrastructure.queue;

public enum JobCriticality {
    /** Retry + DLQ + Sentry alert on final failure. Data must not be lost. */
    CRITICAL,
    /** Retry + DLQ + Sentry warning on final failure. Safe to skip. */
    NON_CRITICAL,
    /** No retry. Log error only — job recurs on schedule. */
    SCHEDULED
}
