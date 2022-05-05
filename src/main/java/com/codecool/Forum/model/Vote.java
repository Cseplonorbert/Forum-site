package com.codecool.Forum.model;

public enum Vote {
    UP(1),
    DOWN(-1);

    private final int value;

    Vote(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
