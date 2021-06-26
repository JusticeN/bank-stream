package com.db.frontoffice.exception;

import com.db.frontoffice.dto.ErrorDataDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        var messages = List.of(ex.getMessage());
        ErrorDataDto errorDataDto = ErrorDataDto.builder()
                .numberOfViolations(messages.size())
                .messages(messages).build();
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(errorDataDto);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        super.handleExceptionInternal(ex, body, headers, status, request);
        if (ex instanceof FailledValidationException) {
            var failledValidationException = (FailledValidationException) ex;
            ErrorDataDto errorData = failledValidationException.getErrorData();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(errorData);
        }
        var messages = List.of(ex.getMessage());
        ErrorDataDto errorDataDto = ErrorDataDto.builder()
                .numberOfViolations(messages.size())
                .messages(messages).build();
        return ResponseEntity.status(status).body(errorDataDto);
    }


    @ExceptionHandler(value = {FailledValidationException.class})
    protected ResponseEntity<Object> handleValidationException(FailledValidationException ex, WebRequest request) {
        ErrorDataDto errorData = ex.getErrorData();
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(errorData);
    }
}
