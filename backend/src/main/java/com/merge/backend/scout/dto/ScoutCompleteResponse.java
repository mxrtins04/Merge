package com.merge.backend.scout.dto;

import com.merge.backend.personalisation.domain.LearningApproach;
import com.merge.backend.personalisation.domain.MotivationType;
import com.merge.backend.personalisation.domain.PriorExposure;
import com.merge.backend.personalisation.domain.ScaffoldingLevel;
import com.merge.backend.personalisation.domain.ThinkingStyle;

public record ScoutCompleteResponse(
        Long studentId,
        String currentStage,
        ThinkingStyle thinkingStyle,
        MotivationType motivationType,
        PriorExposure priorExposure,
        LearningApproach learningApproach,
        ScaffoldingLevel scaffoldingLevel,
        String redirectTarget
) {}
