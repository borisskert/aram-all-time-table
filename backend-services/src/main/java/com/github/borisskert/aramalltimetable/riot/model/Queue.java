package com.github.borisskert.aramalltimetable.riot.model;

public enum Queue {

    ARAM("450");

    private final String value;

    private Queue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
