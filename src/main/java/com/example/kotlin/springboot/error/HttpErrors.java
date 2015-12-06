package com.example.kotlin.springboot.error;

import org.springframework.http.HttpStatus;

public interface HttpErrors {
    HttpStatus getStatus();
    String getMessage();
    String name();
}
