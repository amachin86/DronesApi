package com.example.dronesapi.exeption;

import com.example.dronesapi.dto.response.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ControllerAdvice
public class Exception {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    @ExceptionHandler(value = { RuntimeException.class })
    public ResponseEntity<MessageResponse> handleInvalidInputException(RuntimeException ex) {

        logger.error("Invalid Input Exception: ", ex.getMessage());

        System.out.println("exception is "+ ex.getMessage());

        return new ResponseEntity<MessageResponse>(new MessageResponse("failed",ex.getMessage(),java.time.LocalDateTime.now()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { HttpClientErrorException.Unauthorized.class })

    public ResponseEntity<MessageResponse> handleUnauthorizedException(HttpClientErrorException.Unauthorized ex) {

        logger.error("Unauthorized Exception: ", ex.getMessage());

        return new ResponseEntity<MessageResponse>(new MessageResponse("failed",ex.getMessage(),java.time.LocalDateTime.now()), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(value = { java.lang.Exception.class })

    public ResponseEntity<MessageResponse> handleException(java.lang.Exception ex) {

        logger.error("Exception: ", ex.getMessage());
        return new ResponseEntity<MessageResponse>(new MessageResponse("failed",ex.getMessage(),java.time.LocalDateTime.now()), HttpStatus.INTERNAL_SERVER_ERROR);

    }

}
