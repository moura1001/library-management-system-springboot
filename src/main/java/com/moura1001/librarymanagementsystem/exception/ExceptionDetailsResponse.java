package com.moura1001.librarymanagementsystem.exception;

import java.time.LocalDateTime;
import java.util.Map;

public record ExceptionDetailsResponse(
        String title,
        LocalDateTime timestamp,
        int status,
        String exception,
        Map<String, String> details
) {
}
