package com.example.stylish.util;

import com.example.stylish.dto.ErrorResponse;
import com.example.stylish.exception.*;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoTokenException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(NoTokenException ex) {
        ErrorResponse errorResponse = new ErrorResponse("Unauthorized", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED); // 401
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        ErrorResponse errorResponse = new ErrorResponse("Email already exists", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN); // 403
    }

    @ExceptionHandler(ClientErrorException.class)
    public ResponseEntity<ErrorResponse> handleClientErrorException(ClientErrorException ex) {
        ErrorResponse errorResponse = new ErrorResponse("Client error", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST); // 400
    }

    @ExceptionHandler(SignInFailedException.class)
    public ResponseEntity<ErrorResponse> handleSignInFailedException(SignInFailedException ex) {
        ErrorResponse errorResponse = new ErrorResponse("Sign In Failed", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN); // 403
    }

    @ExceptionHandler(WrongTokenException.class)
    public ResponseEntity<ErrorResponse> handleWrongTokenException(WrongTokenException ex) {
        ErrorResponse errorResponse = new ErrorResponse("WrongToken", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN); // 403
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorResponse> handleIOException(IOException ex) {
        ErrorResponse errorResponse = new ErrorResponse("File error", "An error occurred while processing the file");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR); // 500
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse> handleDataAccessException(DataAccessException ex) {
        ErrorResponse errorResponse = new ErrorResponse("Database Error", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR); // 500
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse("Server error", "An unexpected error occurred");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR); // 500
    }
}
