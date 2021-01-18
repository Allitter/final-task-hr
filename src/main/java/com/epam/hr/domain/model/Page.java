package com.epam.hr.domain.model;

import java.util.Objects;

public class Page {
    private final int currentPage;
    private final int numberOfPages;

    public Page(int currentPage, int numberOfPages) {
        this.currentPage = currentPage;
        this.numberOfPages = numberOfPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    @Override
    public String toString() {
        return "Page{" +
                "currentPage=" + currentPage +
                ", numberOfPages=" + numberOfPages +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Page page = (Page) o;
        return currentPage == page.currentPage
                && numberOfPages == page.numberOfPages;
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentPage, numberOfPages);
    }
}
