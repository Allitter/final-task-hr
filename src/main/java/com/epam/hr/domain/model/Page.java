package com.epam.hr.domain.model;

import java.util.Objects;

public class Page {
    private final int currentPage;
    private final int numberOfPages;
    private final int firstRecordNumber;

    public Page(int currentPage, int numberOfPages, int firstRecordNumber) {
        this.currentPage = currentPage;
        this.numberOfPages = numberOfPages;
        this.firstRecordNumber = firstRecordNumber;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public int getFirstRecordNumber() {
        return firstRecordNumber;
    }

    @Override
    public String toString() {
        return "Page{" +
                "currentPage=" + currentPage +
                ", numberOfPages=" + numberOfPages +
                ", fromRecord=" + firstRecordNumber +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Page page = (Page) o;
        return currentPage == page.currentPage
                && numberOfPages == page.numberOfPages
                && firstRecordNumber == page.firstRecordNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentPage, numberOfPages, firstRecordNumber);
    }
}
