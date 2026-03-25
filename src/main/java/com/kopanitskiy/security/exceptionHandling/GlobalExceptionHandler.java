package com.kopanitskiy.security.exceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<UserIncorrectData> handleUsernameNotFoundException(
            UsernameNotFoundException ex, WebRequest request) {

        UserIncorrectData errorResponse = new UserIncorrectData(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsernameExistsException.class)
    public ResponseEntity<UserIncorrectData> handleUsernameExistException(
            UsernameExistsException exception, WebRequest request) {

        UserIncorrectData errorResponse = new UserIncorrectData(exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<UserIncorrectData> handleGenericException(
            Exception exception, WebRequest request) {

        UserIncorrectData errorResponse = new UserIncorrectData("Произошла ошибка: " + exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}

