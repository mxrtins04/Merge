package com.merge.backend.scout.service;

import com.merge.backend.identity.domain.Student;
import com.merge.backend.identity.repository.StudentRepository;
import com.merge.backend.personalisation.service.ScoutAttributeDerivationService;
import com.merge.backend.scout.domain.ScoutAssessment;
import com.merge.backend.scout.dto.*;
import com.merge.backend.scout.repository.ScoutAssessmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
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

    private static final List<Layer2Problem> LAYER_2_PROBLEMS = List.of(
            new Layer2Problem("p1", "You're asked to build a system for a library to track borrowed books. Before writing any code, walk through how you'd break this problem into smaller pieces."),
            new Layer2Problem("p2", "A user reports 'the app is slow.' Describe your step-by-step process for figuring out why, without writing any code."),
            new Layer2Problem("p3", "You need to sort a stack of 100 numbered exam papers by student ID as quickly as possible, by hand. Describe the approach you'd take and why."),
            new Layer2Problem("p4", "Explain what a 'function' is to someone who has never programmed, using an analogy from everyday life.")
    );

    private final ScoutAssessmentRepository assessmentRepository;
    private final StudentRepository studentRepository;
    private final ScoutAttributeDerivationService derivationService;

    public ScoutServiceImpl(ScoutAssessmentRepository assessmentRepository,
                            StudentRepository studentRepository,
                            ScoutAttributeDerivationService derivationService) {
        this.assessmentRepository = assessmentRepository;
        this.studentRepository = studentRepository;
        this.derivationService = derivationService;
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

        // Students with no prior experience skip Layer 3 — scout is complete after Layer 1.
        if (!hasPriorCodingExperience(assessment)) {
            derivationService.derive(assessment);
        }

        return new Layer1SubmitResponse(
                assessment.getId(),
                student.getId(),
                assessment.getLayer1Responses(),
                assessment.getSubmittedAt()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Layer2ProblemsResponse getLayer2Problems() {
        return new Layer2ProblemsResponse(LAYER_2_PROBLEMS);
    }

    @Override
    public Layer2SubmitResponse submitLayer2(String studentEmail, Layer2SubmitRequest request) {
        Student student = studentRepository.findByEmail(studentEmail)
                .orElseThrow(() -> new IllegalArgumentException("Student not found: " + studentEmail));

        ScoutAssessment assessment = assessmentRepository.findByStudentId(student.getId())
                .orElseThrow(() -> new IllegalArgumentException("Layer 1 assessment must be submitted before Layer 2"));

        if (assessment.getLayer2Results() != null) {
            throw new AlreadySubmittedException("Layer 2 assessment already submitted");
        }

        assessment.setLayer2Results(request.results());
        assessment.setLayer2SubmittedAt(Instant.now());
        assessment = assessmentRepository.save(assessment);

        return new Layer2SubmitResponse(
                assessment.getId(),
                student.getId(),
                assessment.getLayer2Results(),
                assessment.getLayer2SubmittedAt()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Layer3TaskResponse getLayer3Task(String studentEmail) {
        Student student = studentRepository.findByEmail(studentEmail)
                .orElseThrow(() -> new IllegalArgumentException("Student not found: " + studentEmail));

        ScoutAssessment assessment = assessmentRepository.findByStudentId(student.getId())
                .orElseThrow(() -> new NoLayer1SubmissionException("Layer 1 must be completed before accessing Layer 3"));

        if (assessment.getLayer2Results() == null) {
            throw new NoLayer1SubmissionException("Layer 2 must be completed before accessing Layer 3");
        }

        if (!hasPriorCodingExperience(assessment)) {
            return Layer3TaskResponse.notEligible();
        }

        return new Layer3TaskResponse(
                true,
                LAYER_3_TASK_ID,
                LAYER_3_TASK_TITLE,
                LAYER_3_TASK_DESCRIPTION,
                LAYER_3_EXAMPLE_INPUT,
                LAYER_3_EXAMPLE_OUTPUT
        );
    }

    @Override
    public Layer3SubmitResponse submitLayer3(String studentEmail, Layer3SubmitRequest request) {
        Student student = studentRepository.findByEmail(studentEmail)
                .orElseThrow(() -> new IllegalArgumentException("Student not found: " + studentEmail));

        ScoutAssessment assessment = assessmentRepository.findByStudentId(student.getId())
                .orElseThrow(() -> new NoLayer1SubmissionException("Layer 1 must be completed before submitting Layer 3"));

        if (assessment.getLayer2Results() == null) {
            throw new NoLayer1SubmissionException("Layer 2 must be completed before submitting Layer 3");
        }

        if (!hasPriorCodingExperience(assessment)) {
            return new Layer3SubmitResponse(assessment.getId(), student.getId(), true, assessment.getSubmittedAt());
        }

        assessment.setLayer3Code(request.code());
        assessment = assessmentRepository.save(assessment);

        // Scout is now complete for this student — derive attributes and write initial profile.
        derivationService.derive(assessment);

        return new Layer3SubmitResponse(assessment.getId(), student.getId(), false, assessment.getSubmittedAt());
    }

    // ── Private helpers ───────────────────────────────────────────────────────

    /**
     * A q4 answer longer than 15 characters indicates the student described experience they have.
     * Short answers ("No.", "Never", "Not really.") fall below the threshold and are treated as
     * no prior experience.
     */
    private boolean hasPriorCodingExperience(ScoutAssessment assessment) {
        String q4 = assessment.getLayer1Responses().getOrDefault("q4", "").strip();
        return q4.length() > 15;
    }

    private static final String LAYER_3_TASK_ID          = "baseline-filter-evens";
    private static final String LAYER_3_TASK_TITLE        = "Filter Even Numbers";
    private static final String LAYER_3_TASK_DESCRIPTION  = """
            Write a function that accepts a list of integers and returns only the even numbers \
            from that list, preserving their original order. If the input list is empty, return \
            an empty list. Use any programming language you are comfortable with.""";
    private static final String LAYER_3_EXAMPLE_INPUT     = "[1, 2, 3, 4, 5, 6]";
    private static final String LAYER_3_EXAMPLE_OUTPUT    = "[2, 4, 6]";
}
