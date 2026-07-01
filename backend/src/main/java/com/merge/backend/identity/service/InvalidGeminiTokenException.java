package com.merge.backend.identity.service;

public class InvalidGeminiTokenException extends RuntimeException {
    public InvalidGeminiTokenException(String message) {
        super(message);
    }
}
