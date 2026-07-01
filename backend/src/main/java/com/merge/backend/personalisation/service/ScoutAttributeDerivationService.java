package com.merge.backend.personalisation.service;

import com.merge.backend.personalisation.domain.*;
import com.merge.backend.personalisation.repository.PersonalisationProfileRepository;
import com.merge.backend.scout.domain.ScoutAssessment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Derives four learner attributes from Scout Layer 1 (and optionally Layer 3) responses,
 * then writes an initial PersonalisationProfile that drives all downstream AI calls.
 *
 * Called once per student at the end of the Scout flow:
 *   - after Layer 1 submit when no prior experience (Layer 3 skipped)
 *   - after Layer 3 submit when prior experience exists
 *
 * Attributes are derived via keyword-frequency heuristics — no AI call is made.
 * All four attributes are set once here and are never overwritten by session-based AI updates.
 * Initial scaffolding_level is MEDIUM (= level 3 on the conceptual 1–5 scale).
 */
@Service
@Transactional
public class ScoutAttributeDerivationService {

    private static final Logger log = LoggerFactory.getLogger(ScoutAttributeDerivationService.class);

    private final PersonalisationProfileRepository profileRepository;

    public ScoutAttributeDerivationService(PersonalisationProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    /**
     * Derives attributes from {@code assessment} and persists an initial PersonalisationProfile.
     * If a profile already exists for the student (e.g. retry), its attributes are refreshed.
     */
    public PersonalisationProfile derive(ScoutAssessment assessment) {
        Map<String, String> r = assessment.getLayer1Responses();

        ThinkingStyle  thinkingStyle  = deriveThinkingStyle(r);
        MotivationType motivationType = deriveMotivationType(r);
        PriorExposure  priorExposure  = derivePriorExposure(assessment);
        LearningApproach learningApproach = deriveLearningApproach(r);

        PersonalisationProfile profile = profileRepository
                .findByStudentId(assessment.getStudent().getId())
                .orElseGet(() -> {
                    PersonalisationProfile p = new PersonalisationProfile();
                    p.setStudent(assessment.getStudent());
                    return p;
                });

        // Only set session-derived fields when the profile is brand new.
        // Existing profiles keep their AI-updated weak/strength concepts and hint patterns.
        if (profile.getId() == null) {
            profile.setScaffoldingLevel(ScaffoldingLevel.MEDIUM);
            profile.setWeakConcepts(Collections.emptyList());
            profile.setStrengthConcepts(Collections.emptyList());
        }

        profile.setThinkingStyle(thinkingStyle);
        profile.setMotivationType(motivationType);
        profile.setPriorExposure(priorExposure);
        profile.setLearningApproach(learningApproach);
        profile.setUpdatedAt(Instant.now());

        PersonalisationProfile saved = profileRepository.save(profile);

        log.info("[ScoutDerivation] student={} thinking={} motivation={} exposure={} learning={}",
                assessment.getStudent().getId(), thinkingStyle, motivationType, priorExposure, learningApproach);

        return saved;
    }

    // ── Derivation heuristics ─────────────────────────────────────────────────

    /**
     * SYSTEMATIC vs INTUITIVE — from q3 (why CS), q4 (prior experience), q7 (learning worry).
     * Systematic vocabulary: logic, structure, order, algorithms.
     * Intuitive vocabulary: curiosity, creativity, exploration.
     * Default: SYSTEMATIC.
     */
    private ThinkingStyle deriveThinkingStyle(Map<String, String> r) {
        String text = join(r, "q3", "q4", "q7");
        int systematic = count(text, "logic", "structure", "systematic", "methodical", "organiz", "algorithm",
                "math", "precise", "analyz", "step", "plan", "order", "pattern", "rules");
        int intuitive  = count(text, "curious", "curiosity", "creat", "intuition", "explor", "experiment",
                "feel", "wonder", "natural", "flow", "imagin", "interest", "inspir");
        return intuitive > systematic ? ThinkingStyle.INTUITIVE : ThinkingStyle.SYSTEMATIC;
    }

    /**
     * EXTERNAL vs INTERNAL — from q5 (what SE means), q8 (5-year career vision).
     * External vocabulary: salary, job, career, security.
     * Internal vocabulary: passion, impact, curiosity, love of building.
     * Default: INTERNAL.
     */
    private MotivationType deriveMotivationType(Map<String, String> r) {
        String text = join(r, "q5", "q8");
        int external = count(text, "salary", "job", "money", "income", "career", "employ", "company",
                "pay", "hire", "secur", "stability", "promotion", "title", "earn");
        int internal  = count(text, "passion", "love", "build", "creat", "impact", "change", "curious",
                "enjoy", "fun", "interest", "purpose", "meaning", "contribut", "help");
        return external > internal ? MotivationType.EXTERNAL : MotivationType.INTERNAL;
    }

    /**
     * NONE / SOME / EXPERIENCED — from q4 answer length and presence of Layer 3 code.
     * EXPERIENCED: student submitted the Layer 3 baseline task (layer3_code not null).
     * SOME: q4 answer > 15 chars but no code submitted.
     * NONE: q4 answer ≤ 15 chars (short "No" answer).
     */
    private PriorExposure derivePriorExposure(ScoutAssessment assessment) {
        if (assessment.getLayer3Code() != null) return PriorExposure.EXPERIENCED;
        String q4 = assessment.getLayer1Responses().getOrDefault("q4", "").strip();
        return q4.length() > 15 ? PriorExposure.SOME : PriorExposure.NONE;
    }

    /**
     * EXAMPLES_FIRST vs DEFINITIONS_FIRST — from q3 (why CS) and q7 (learning worry).
     * Examples vocabulary: hands-on, practical, project, try, apply, build.
     * Definitions vocabulary: understand, theory, concept, why, foundation, principle.
     * Default: EXAMPLES_FIRST.
     */
    private LearningApproach deriveLearningApproach(Map<String, String> r) {
        String text = join(r, "q3", "q7");
        int examples     = count(text, "example", "hands-on", "practical", "project", "build", "try",
                "do", "practice", "apply", "show", "demo", "real");
        int definitions  = count(text, "understand", "theory", "concept", "fundamental", "why",
                "foundation", "principle", "study", "read", "textbook", "abstract", "deep");
        return definitions > examples ? LearningApproach.DEFINITIONS_FIRST : LearningApproach.EXAMPLES_FIRST;
    }

    // ── Utilities ─────────────────────────────────────────────────────────────

    private String join(Map<String, String> r, String... keys) {
        return Arrays.stream(keys)
                .map(k -> r.getOrDefault(k, ""))
                .collect(java.util.stream.Collectors.joining(" "))
                .toLowerCase();
    }

    private int count(String text, String... keywords) {
        return (int) Arrays.stream(keywords).filter(text::contains).count();
    }
}
