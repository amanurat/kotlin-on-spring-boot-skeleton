package com.example.kotlin.springboot.error;

import java.text.MessageFormat;

public class AppError extends RuntimeException {
    Object[] args;
    Throwable cause;
    private HttpErrors error;

    public AppError(HttpErrors error, Throwable cause, String... args) {
        super();
        this.error = error;
        this.cause = cause;
        this.args = args;
    }

    @Override
    public String getMessage() {
        if(args != null) {
            return error.name() + ": " + MessageFormat.format(error.getMessage(), args);
        } else {
            return error.name() + ": " + error.getMessage();
        }
    }

    public HttpErrors getError() {
        return error;
    }
}
