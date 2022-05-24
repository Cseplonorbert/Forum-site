package com.codecool.Forum.exception;

public class PageNotFoundException extends RuntimeException{
    public PageNotFoundException(Integer requestedPageNumber, Integer totalPageNumber) {
        super(String.format(" The requested page number %d is bigger than the total number of pages (%d)",
                requestedPageNumber, totalPageNumber));
    }
}
