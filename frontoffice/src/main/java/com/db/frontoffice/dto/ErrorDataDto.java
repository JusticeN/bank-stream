package com.db.frontoffice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ErrorDataDto {
    private int numberOfViolations;
    private List<String> messages;
}
