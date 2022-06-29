package com.n75jr.nexign.lottery.controller;

import com.n75jr.nexign.lottery.domain.ErrorResponse;
import com.n75jr.nexign.lottery.domain.ValidationErrorResponse;
import com.n75jr.nexign.lottery.domain.Violation;
import com.n75jr.nexign.lottery.exception.NotEnoughParticipantsException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.time.ZonedDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Log4j2
@RestControllerAdvice
public class ExceptionHandlerAdviceController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ValidationErrorResponse onMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        if (log.isDebugEnabled()) {
            log.debug("Handling MethodArgumentNotValidException...");
        }
        var violations = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> Violation.of(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(toList());
        return ValidationErrorResponse.of(violations);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ValidationErrorResponse onConstraintViolationException(ConstraintViolationException ex) {
        if (log.isDebugEnabled()) {
            log.debug("Handling ConstraintViolationException...");
        }
        var violations = ex.getConstraintViolations().stream()
                .map(cv -> Violation.of(
                        cv.getPropertyPath().toString(),
                        cv.getMessage()
                ))
                .collect(toList());
        return ValidationErrorResponse.of(violations);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ValidationErrorResponse onHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        if (log.isDebugEnabled()) {
            log.debug("Handling HttpMessageNotReadableException...");
        }
        return ValidationErrorResponse.of(List.of(
                Violation.of(
                        "json",
                        "not valid format"
                )
        ));
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(NotEnoughParticipantsException.class)
    public ErrorResponse onNotEnoughParticipantsException(NotEnoughParticipantsException ex) {
        if (log.isDebugEnabled()) {
            log.debug("Handling NotEnoughParticipantsException...");
        }
        return ErrorResponse.of(
                "not enough participants for starting of the lottery",
                "200",
                ZonedDateTime.now()
        );
    }
}
