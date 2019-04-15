package com.research.demo.exception;

public class InvalidExpressionException extends Exception{

    private String message;

    public InvalidExpressionException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
