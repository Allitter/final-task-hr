package com.epam.hr.domain.controller.command.impl;

import com.epam.hr.domain.controller.command.Command;
import com.epam.hr.domain.model.Page;

/**
 * provides skeletal implementation methods to use in heir classes
 */
public abstract class AbstractCommand implements Command {
    protected static final int DEFAULT_NUMBER_OF_RECORDS_PER_PAGE = 10;

    /**
     * Template method that creates {@link Page page} that needed for
     * {@link com.epam.hr.tag.PageBarTag page bar tag} to create page bar
     *
     * @param queriedPage          number of page from request
     * @param totalRecordsQuantity total number of available records
     * @return {@link Page page} that contains closest available pag number and total number of pages
     */
    public final Page getClosestExistingPage(int queriedPage, int totalRecordsQuantity) {
        int page = Math.max(queriedPage, 0);

        final int numberOfRecordsPerPage = getNumberOfRecordsPerPage();
        int numberOfPages = totalRecordsQuantity / numberOfRecordsPerPage;
        numberOfPages = totalRecordsQuantity % numberOfRecordsPerPage == 0 ? numberOfPages : numberOfPages + 1;

        if (numberOfPages != 0 && page >= numberOfPages) {
            page--;
        }

        int fromRecord = page * getNumberOfRecordsPerPage();

        return new Page(page, numberOfPages, fromRecord);
    }

    /**
     * Provides {@link AbstractCommand#getClosestExistingPage(int, int)} with
     * number of records per page can be redefined in heirs
     *
     * @return number of records per page
     */
    protected int getNumberOfRecordsPerPage() {
        return DEFAULT_NUMBER_OF_RECORDS_PER_PAGE;
    }
}
