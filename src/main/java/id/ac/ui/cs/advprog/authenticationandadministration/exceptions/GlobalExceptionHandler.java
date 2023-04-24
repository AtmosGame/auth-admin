package id.ac.ui.cs.advprog.authenticationandadministration.exceptions;

import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.auth.UserDoesNotExistException;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.auth.UserHasBeenBlockedException;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.auth.UserIsAdministratorException;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.auth.UsernameAlreadyExistsException;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.report.ReportDoesNotExistException;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.report.UserAndReportNotMatchedException;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.report.UserDoesNotHaveReportException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {UsernameAlreadyExistsException.class})
    public ResponseEntity<Object> userExist(){
        ErrorTemplate baseException = new ErrorTemplate(
                "User with the same email already exist",
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("Asia/Jakarta"))
        );

        return new ResponseEntity<>(baseException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {UserDoesNotExistException.class, ReportDoesNotExistException.class})
    public ResponseEntity<Object> userOrReportNotExist(Exception exception){
        ErrorTemplate baseException = new ErrorTemplate(
                exception.getMessage(),
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now(ZoneId.of("Asia/Jakarta"))
        );

        return new ResponseEntity<>(baseException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {UserIsAdministratorException.class})
    public ResponseEntity<Object> userIsAdministrator(Exception exception){
        ErrorTemplate baseException = new ErrorTemplate(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("Asia/Jakarta"))
        );

        return new ResponseEntity<>(baseException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {UserHasBeenBlockedException.class})
    public ResponseEntity<Object> userHasBeenBlocked(Exception exception){
        ErrorTemplate baseException = new ErrorTemplate(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("Asia/Jakarta"))
        );

        return new ResponseEntity<>(baseException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {UserDoesNotHaveReportException.class})
    public ResponseEntity<Object> userDoesNotHaveReport(Exception exception){
        ErrorTemplate baseException = new ErrorTemplate(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("Asia/Jakarta"))
        );

        return new ResponseEntity<>(baseException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {UserAndReportNotMatchedException.class})
    public ResponseEntity<Object> userAndReportNotMatched(Exception exception){
        ErrorTemplate baseException = new ErrorTemplate(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("Asia/Jakarta"))
        );

        return new ResponseEntity<>(baseException, HttpStatus.BAD_REQUEST);
    }
}