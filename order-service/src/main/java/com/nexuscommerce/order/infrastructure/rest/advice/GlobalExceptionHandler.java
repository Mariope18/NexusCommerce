package com.nexuscommerce.order.infrastructure.rest.advice;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ProblemDetail entityNotFoundException(EntityNotFoundException ex) {

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND, ex.getMessage()
        );
        problemDetail.setTitle("Risorsa non trovata");

        return problemDetail;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail illegalArgumentException(IllegalArgumentException ex) {

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, ex.getMessage()
        );
        problemDetail.setTitle("Argomento non valido");

        return problemDetail;
    }

    @ExceptionHandler(IllegalStateException.class)
    public ProblemDetail illegalStateException(IllegalStateException ex) {

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, ex.getMessage()
        );
        problemDetail.setTitle("Stato non valido");

        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail methodArgumentNotValidException(MethodArgumentNotValidException ex) {

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, "La richiesta contiene campi non validi"
        );
        problemDetail.setTitle("Validazione fallita");

        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> fieldErrors.put(error.getField(), error.getDefaultMessage()));

        problemDetail.setProperty("invalid_fields", fieldErrors);

        return problemDetail;
    }
}
