package com.codecool.Forum.model;

public enum OrderBy {
    TITLE("title"),
    SUBMISSION_TIME("createdOn"),
    DESCRIPTION("description"),
    NUMBER_OF_VIEWS("viewed"),
    NUMBER_OF_VOTES("votes");

    private final String fieldName;

    OrderBy(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
