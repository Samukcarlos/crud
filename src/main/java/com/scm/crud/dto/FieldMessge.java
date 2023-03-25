package com.scm.crud.dto;

public class FieldMessge {
    private String fieldName;
    private String message;

    public FieldMessge(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getMessage() {
        return message;
    }
}
