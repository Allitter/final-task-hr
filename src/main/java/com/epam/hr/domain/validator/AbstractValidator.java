package com.epam.hr.domain.validator;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractValidator {
    protected static final String NULL_OR_EMPTY = "NullOrEmpty";
    protected static final String REGEX_FAIL = "RegexFail";
    protected static final String TOO_WEAK = "TooWeak";
    protected static final String OUT_OF_BOUNDS = "OutOfBounds";
    protected static final String PARSE_FAIL = "ParseFail";

    protected List<String> nullOrEmptyAndRegexCheck(String target, String targetName, String regex) {
        List<String> fails = new LinkedList<>();
        if (target == null || "".equals(target)) {
            fails.add(targetName + NULL_OR_EMPTY);
        } else if (!target.matches(regex)) {
            fails.add(targetName + REGEX_FAIL);
        }

        return fails;
    }
}
