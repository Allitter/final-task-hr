package com.epam.hr.domain.controller.command.impl;

import com.epam.hr.domain.controller.command.Command;
import com.epam.hr.domain.model.Page;

public abstract class AbstractCommand implements Command {
    private static final int DEFAULT_NUMBER_OF_RECORDS_PER_PAGE = 10;

    public final Page getClosestExistingPage(int queriedPage, int totalRecordsQuantity) {
        int page = Math.max(queriedPage, 0);

        final int numberOfRecordsPerPage = getNumberOfRecordsPerPage();
        int numberOfPages = totalRecordsQuantity / numberOfRecordsPerPage;
        numberOfPages = totalRecordsQuantity % numberOfRecordsPerPage == 0 ? numberOfPages : numberOfPages + 1;

        if (numberOfPages != 0 && page >= numberOfPages) {
            page--;
        }

        return new Page(page, numberOfPages);
    }

    protected int getNumberOfRecordsPerPage() {
        return DEFAULT_NUMBER_OF_RECORDS_PER_PAGE;
    }
}
