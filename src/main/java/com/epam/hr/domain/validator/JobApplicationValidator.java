package com.epam.hr.domain.validator;

import com.epam.hr.domain.model.JobApplication;

import java.util.LinkedList;
import java.util.List;

public class JobApplicationValidator extends AbstractValidator {
    private static final String PRELIMINARY_INTERVIEW_NOTE = "preliminaryInterviewNote";
    private static final String TECHNICAL_INTERVIEW_NOTE = "technicalInterviewNote";
    private static final String INTERVIEW_NOTE_REGEX = "(.|\\n|\\r){3,1048}";

    public List<String> validate(JobApplication jobApplication) {
        String preliminaryInterviewNote = jobApplication.getPreliminaryInterviewNote();
        String technicalInterviewNote = jobApplication.getTechnicalInterviewNote();

        List<String> result = new LinkedList<>();
        result.addAll(validatePreliminaryInterviewNote(preliminaryInterviewNote));
        result.addAll(validateTechnicalInterviewNote(technicalInterviewNote));
        return result;
    }

    public List<String> validatePreliminaryInterviewNote(String preliminaryInterviewNote) {
        return nullOrEmptyAndRegexCheck(preliminaryInterviewNote, PRELIMINARY_INTERVIEW_NOTE, INTERVIEW_NOTE_REGEX);
    }

    public List<String> validateTechnicalInterviewNote(String technicalInterviewNote) {
        return nullOrEmptyAndRegexCheck(technicalInterviewNote, TECHNICAL_INTERVIEW_NOTE, INTERVIEW_NOTE_REGEX);
    }
}
