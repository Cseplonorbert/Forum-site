package com.codecool.Forum.model;

public enum SortBy {
    TITLE("title"),
    SUBMISSION_TIME("submissionTime"),
    DESCRIPTION("message"),
    NUMBER_OF_VIEWS("viewNumber"),
    NUMBER_OF_VOTES("voteNumber");

    private final String fieldName;

    SortBy(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
