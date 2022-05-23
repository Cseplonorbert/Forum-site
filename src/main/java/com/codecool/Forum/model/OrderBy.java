package com.codecool.Forum.model;

public enum OrderBy {
    TITLE("title"),
    SUBMISSION_TIME("submissionTime"),
    DESCRIPTION("message"),
    NUMBER_OF_VIEWS("viewNumber"),
    NUMBER_OF_VOTES("voteNumber");

    private final String fieldName;

    OrderBy(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
