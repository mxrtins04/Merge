package com.merge.backend.scout.service;

import com.merge.backend.scout.dto.*;

public interface ScoutService {
    Layer1QuestionsResponse getLayer1Questions();
    Layer1SubmitResponse submitLayer1(String studentEmail, Layer1SubmitRequest request);
    Layer2ProblemsResponse getLayer2Problems();
    Layer2SubmitResponse submitLayer2(String studentEmail, Layer2SubmitRequest request);
    Layer3TaskResponse getLayer3Task(String studentEmail);
    Layer3SubmitResponse submitLayer3(String studentEmail, Layer3SubmitRequest request);
}
