package id.ac.ui.cs.advprog.authenticationandadministration.exceptions.advice;

import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.UserDoesNotExistException;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.UsernameAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.ErrorTemplate;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {UsernameAlreadyExistsException.class})
    public ResponseEntity<Object> userExist(){
        ErrorTemplate baseException = new ErrorTemplate(
                "User with the same email already exist",
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(baseException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {UserDoesNotExistException.class})
    public ResponseEntity<Object> userNotExist(Exception exception){
        ErrorTemplate baseException = new ErrorTemplate(
                exception.getMessage(),
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(baseException, HttpStatus.NOT_FOUND);
    }
}
