package com.merge.backend.scout.service;

import com.merge.backend.identity.domain.Student;
import com.merge.backend.identity.repository.StudentRepository;
import com.merge.backend.scout.domain.ScoutAssessment;
import com.merge.backend.scout.dto.*;
import com.merge.backend.scout.repository.ScoutAssessmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ScoutServiceImpl implements ScoutService {

    private static final List<Layer1Question> LAYER_1_QUESTIONS = List.of(
            new Layer1Question("q1", "Tell us about yourself in your own words."),
            new Layer1Question("q2", "Which university are you attending, and what year are you in?"),
            new Layer1Question("q3", "What made you choose Computer Science or a related field?"),
            new Layer1Question("q4", "Have you written code before? If yes, describe what you built."),
            new Layer1Question("q5", "What does becoming a software engineer mean to you personally?"),
            new Layer1Question("q6", "How many hours per week can you realistically commit to this programme?"),
            new Layer1Question("q7", "What is your biggest worry about learning to code professionally?"),
            new Layer1Question("q8", "What does your ideal engineering career look like in five years?")
    );

    private final ScoutAssessmentRepository assessmentRepository;
    private final StudentRepository studentRepository;

    public ScoutServiceImpl(ScoutAssessmentRepository assessmentRepository,
                            StudentRepository studentRepository) {
        this.assessmentRepository = assessmentRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Layer1QuestionsResponse getLayer1Questions() {
        return new Layer1QuestionsResponse(LAYER_1_QUESTIONS);
    }

    @Override
    public Layer1SubmitResponse submitLayer1(String studentEmail, Layer1SubmitRequest request) {
        Student student = studentRepository.findByEmail(studentEmail)
                .orElseThrow(() -> new IllegalArgumentException("Student not found: " + studentEmail));

        if (assessmentRepository.existsByStudentId(student.getId())) {
            throw new AlreadySubmittedException("Layer 1 assessment already submitted");
        }

        ScoutAssessment assessment = new ScoutAssessment();
        assessment.setStudent(student);
        assessment.setLayer1Responses(request.responses());
        assessment = assessmentRepository.save(assessment);

        return new Layer1SubmitResponse(
                assessment.getId(),
                student.getId(),
                assessment.getLayer1Responses(),
                assessment.getSubmittedAt()
        );
    }
}
