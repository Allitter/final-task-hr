package com.epam.hr.tag;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;


public class PageBarTag extends TagSupport {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final String PAGE_BAR_DIV_START_TAG = "<div class='page_bar'>";
    private static final String PAGE_BAR_DIV_END_TAG = "</div>";
    private static final String SIDE_BUTTON_TAG = "<a class='page_btn' href='%s'>%s</a>";
    private static final String PAGE_BUTTON_TAG = "<a class='page_btn' href='%s'>%d</a>";
    private static final String CURRENT_PAGE_BUTTON_TAG = "<a class='page_btn current_page' href='%s'>%d</a>";
    public static final String PAGER_REFERENCE_TEMPLATE = "%s/controller?command=%s&page=%s";
    private static final int PAGES_FROM_SIDES = 2;
    private static final int SIDE_BUTTONS_MINIMAL_PAGES_AMOUNT = PAGES_FROM_SIDES + 1;
    private static final int MINIMAL_NUMBER_OF_PAGES_TO_SHOW = 2;
    private static final int FIRST_PAGE = 0;
    private static final int PAGE_NUMBER_BIAS = 1;
    private static final String LAST_PAGE_CAPTION = ">>";
    private static final String START_PAGE_CAPTION = "<<";

    private int numberOfPages;
    private int currentPage;
    private String command;

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    @Override
    public int doStartTag() {
        if (numberOfPages < MINIMAL_NUMBER_OF_PAGES_TO_SHOW) {
            return SKIP_BODY;
        }

        try {
            JspWriter out = pageContext.getOut();
            out.println(PAGE_BAR_DIV_START_TAG);
            String pageReference;
            if (numberOfPages > SIDE_BUTTONS_MINIMAL_PAGES_AMOUNT) {
                pageReference = getPageReference(FIRST_PAGE);
                out.println(String.format(SIDE_BUTTON_TAG, pageReference, START_PAGE_CAPTION));
            }

            for (int i = currentPage - PAGES_FROM_SIDES; i <= currentPage + PAGES_FROM_SIDES; i++) {
                if (i < 0 || i >= numberOfPages) {
                    continue;
                }

                pageReference = getPageReference(i);
                if (i == currentPage) {
                    out.println(String.format(CURRENT_PAGE_BUTTON_TAG, pageReference, i + PAGE_NUMBER_BIAS));
                } else {
                    out.println(String.format(PAGE_BUTTON_TAG, pageReference, i + PAGE_NUMBER_BIAS));
                }
            }

            if (numberOfPages > SIDE_BUTTONS_MINIMAL_PAGES_AMOUNT) {
                pageReference = getPageReference(numberOfPages - PAGE_NUMBER_BIAS);
                out.println(String.format(SIDE_BUTTON_TAG, pageReference, LAST_PAGE_CAPTION));
            }

            out.println(PAGE_BAR_DIV_END_TAG);
        } catch (IOException e) {
            LOGGER.error("Error on PageBarTag executing", e);
        }

        return SKIP_BODY;
    }

    private String getPageReference(int pageNumber) {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        return String.format(PAGER_REFERENCE_TEMPLATE, request.getContextPath(), command, pageNumber);
    }
}
