package com.epam.hr.domain.validator;

import com.epam.hr.domain.model.JobApplication;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.hr.domain.validator.ValidationFieldNames.PRELIMINARY_INTERVIEW_NOTE;
import static com.epam.hr.domain.validator.ValidationFieldNames.TECHNICAL_INTERVIEW_NOTE;

public class JobApplicationValidator extends AbstractValidator {
    private static final String INTERVIEW_NOTE_REGEX = "(.|\\n|\\r){3,1048}";

    public Map<String, Boolean> validate(JobApplication jobApplication) {
        String preliminaryInterviewNote = jobApplication.getPreliminaryInterviewNote();
        String technicalInterviewNote = jobApplication.getTechnicalInterviewNote();

        Map<String, Boolean> result = new HashMap<>();
        result.put(PRELIMINARY_INTERVIEW_NOTE ,validatePreliminaryInterviewNote(preliminaryInterviewNote));
        result.put(TECHNICAL_INTERVIEW_NOTE ,validateTechnicalInterviewNote(technicalInterviewNote));
        return result;
    }

    public List<String> getValidationFails(JobApplication jobApplication) {
        return filterFails(validate(jobApplication));
    }

    public boolean validatePreliminaryInterviewNote(String preliminaryInterviewNote) {
        return nullOrEmptyAndRegexCheck(preliminaryInterviewNote, INTERVIEW_NOTE_REGEX);
    }

    public boolean validateTechnicalInterviewNote(String technicalInterviewNote) {
        return nullOrEmptyAndRegexCheck(technicalInterviewNote, INTERVIEW_NOTE_REGEX);
    }
}
