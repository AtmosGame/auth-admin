package id.ac.ui.cs.advprog.authenticationandadministration.exceptions;

import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.auth.*;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.report.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final String zoneId = "Asia/Jakarta";

    @ExceptionHandler(value = {UsernameAlreadyExistsException.class})
    public ResponseEntity<Object> userExist(){
        ErrorTemplate baseException = new ErrorTemplate(
                "User with the same email already exist",
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of(zoneId))
        );

        return new ResponseEntity<>(baseException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {UserDoesNotExistException.class, ReportDoesNotExistException.class})
    public ResponseEntity<Object> userOrReportNotExist(Exception exception){
        ErrorTemplate baseException = new ErrorTemplate(
                exception.getMessage(),
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now(ZoneId.of(zoneId))
        );

        return new ResponseEntity<>(baseException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {UserIsAdministratorException.class})
    public ResponseEntity<Object> userIsAdministrator(Exception exception){
        ErrorTemplate baseException = new ErrorTemplate(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of(zoneId))
        );

        return new ResponseEntity<>(baseException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {UserHasBeenBlockedException.class})
    public ResponseEntity<Object> userHasBeenBlocked(Exception exception){
        ErrorTemplate baseException = new ErrorTemplate(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of(zoneId))
        );

        return new ResponseEntity<>(baseException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {UserDoesNotHaveReportException.class})
    public ResponseEntity<Object> userDoesNotHaveReport(Exception exception){
        ErrorTemplate baseException = new ErrorTemplate(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of(zoneId))
        );

        return new ResponseEntity<>(baseException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {UserAndReportNotMatchedException.class})
    public ResponseEntity<Object> userAndReportNotMatched(Exception exception){
        ErrorTemplate baseException = new ErrorTemplate(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of(zoneId))
        );

        return new ResponseEntity<>(baseException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {InvalidPasswordException.class})
    public ResponseEntity<Object> invalidPassword(Exception exception) {
        ErrorTemplate baseException = new ErrorTemplate(
                "Invalid password",
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of(zoneId))
        );

        return new ResponseEntity<>(baseException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {InvalidTokenException.class})
    public ResponseEntity<Object> invalidToken(Exception exception) {
        ErrorTemplate baseException = new ErrorTemplate(
                "Invalid token",
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of(zoneId))
        );

        return new ResponseEntity<>(baseException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {DuplicateReportException.class})
    public ResponseEntity<Object> duplicateReport(Exception exception) {
        ErrorTemplate baseException = new ErrorTemplate(
                "Cannot report the same user before the admin approves the previous report",
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of(zoneId))
        );

        return new ResponseEntity<>(baseException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {InformationNullException.class})
    public ResponseEntity<Object> informationNull(Exception exception) {
        ErrorTemplate baseException = new ErrorTemplate(
                "Information cannot be empty",
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of(zoneId))
        );

        return new ResponseEntity<>(baseException, HttpStatus.BAD_REQUEST);
    }
}
