package com.epam.hr.domain.validator;

import com.epam.hr.domain.model.JobApplication;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.hr.domain.validator.ValidationFieldNames.PRELIMINARY_INTERVIEW_NOTE;
import static com.epam.hr.domain.validator.ValidationFieldNames.TECHNICAL_INTERVIEW_NOTE;

/**
 * The type Job application validator.
 */
public class JobApplicationValidator extends AbstractValidator {
    private static final String INTERVIEW_NOTE_REGEX = "(.|\\n|\\r){3,1048}";

    /**
     * Validate map.
     *
     * @param jobApplication the job application
     * @return validation results
     */
    public Map<String, Boolean> validate(JobApplication jobApplication) {
        String preliminaryInterviewNote = jobApplication.getPreliminaryInterviewNote();
        String technicalInterviewNote = jobApplication.getTechnicalInterviewNote();

        Map<String, Boolean> result = new HashMap<>();
        result.put(PRELIMINARY_INTERVIEW_NOTE, validatePreliminaryInterviewNote(preliminaryInterviewNote));
        result.put(TECHNICAL_INTERVIEW_NOTE, validateTechnicalInterviewNote(technicalInterviewNote));
        return result;
    }

    /**
     * Gets validation fails.
     *
     * @param jobApplication the job application
     * @return list of validation fails
     */
    public List<String> getValidationFails(JobApplication jobApplication) {
        return filterFails(validate(jobApplication));
    }

    /**
     * Validate preliminary interview note boolean.
     *
     * @param preliminaryInterviewNote the preliminary interview note
     * @return true if preliminary interview note is correct
     */
    public boolean validatePreliminaryInterviewNote(String preliminaryInterviewNote) {
        return nullOrEmptyCheck(preliminaryInterviewNote)
                && regexCheck(preliminaryInterviewNote, INTERVIEW_NOTE_REGEX);
    }

    /**
     * Validate technical interview note boolean.
     *
     * @param technicalInterviewNote the technical interview note
     * @return true if technical interview note
     */
    public boolean validateTechnicalInterviewNote(String technicalInterviewNote) {
        return nullOrEmptyCheck(technicalInterviewNote)
                && regexCheck(technicalInterviewNote, INTERVIEW_NOTE_REGEX);
    }
}
