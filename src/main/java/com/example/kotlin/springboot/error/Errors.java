package com.example.kotlin.springboot.error;

import org.springframework.http.HttpStatus;

public enum Errors implements HttpErrors {
    Unexpected(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server errors. : {0}"),
    Unauthorized(HttpStatus.UNAUTHORIZED, "Unauthorized message received."),
    UserNotFound(HttpStatus.NOT_FOUND, "User not found from {0}"),
    RefreshTokenNotFound(HttpStatus.NOT_FOUND, "Refresh token not found."),
    UserFailedToAuthentication(HttpStatus.BAD_REQUEST, "Failed to authentication user. Invalid login_id or password?"),
    UserFailedToActivation(HttpStatus.BAD_REQUEST, "Failed to activation user."),
    UserFailedToSave(HttpStatus.BAD_REQUEST, "User failed to save on {0}"),
    DataDuplicate(HttpStatus.CONFLICT, "Failed to save. {0} is duplicated."),
    ;

    protected HttpStatus status;
    protected String message;

    Errors(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
