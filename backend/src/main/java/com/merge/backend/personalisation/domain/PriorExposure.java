package com.merge.backend.personalisation.domain;

public enum PriorExposure {
    /** q4 answer ≤ 15 chars — student reported no prior coding. */
    NONE,
    /** q4 answer > 15 chars but no Layer 3 code submitted. */
    SOME,
    /** Student submitted the Layer 3 baseline coding task. */
    EXPERIENCED
}
