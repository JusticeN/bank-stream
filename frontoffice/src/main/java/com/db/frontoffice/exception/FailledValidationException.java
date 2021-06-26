package com.db.frontoffice.exception;

import com.db.frontoffice.dto.ErrorDataDto;

import java.util.List;

public class FailledValidationException extends RuntimeException {

    private ErrorDataDto errorData;

    public FailledValidationException(List<String> validationsErrorMessages) {
        super();
        errorData = ErrorDataDto.builder()
                .numberOfViolations(validationsErrorMessages.size())
                .messages(validationsErrorMessages)
                .build();
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public ErrorDataDto getErrorData() {
        return errorData;
    }

}
