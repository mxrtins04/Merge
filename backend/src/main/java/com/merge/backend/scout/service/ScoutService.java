package com.merge.backend.scout.service;

import com.merge.backend.scout.dto.Layer1QuestionsResponse;
import com.merge.backend.scout.dto.Layer1SubmitRequest;
import com.merge.backend.scout.dto.Layer1SubmitResponse;

public interface ScoutService {
    Layer1QuestionsResponse getLayer1Questions();
    Layer1SubmitResponse submitLayer1(String studentEmail, Layer1SubmitRequest request);
}
