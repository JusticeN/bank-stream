package com.db.frontoffice.exception;

import lombok.Builder;
import lombok.Data;

import java.util.List;

public class FailledValidationException extends Exception {

    private ErrorData errorData;

    public FailledValidationException(List<String> validationsErrorMessages) {
        super();
        errorData = ErrorData.builder()
                .numberOfViolations(validationsErrorMessages.size())
                .messages(validationsErrorMessages)
                .build();
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public ErrorData getErrorData() {
        return errorData;
    }

    @Builder
    @Data
    private static class ErrorData {
        private int numberOfViolations;
        private List<String> messages;
    }
}
