package id.ac.ui.cs.advprog.authenticationandadministration.exceptions;

import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.auth.*;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.report.ReportDoesNotExistException;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.report.UserAndReportNotMatchedException;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.report.UserDoesNotHaveReportException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {
    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    private final String username = "test";
    private final Integer reportId = 1;

    void templateTestException(ResponseEntity<Object> response, String expectedResponseMessage, HttpStatus expectedHttpStatus){
        ErrorTemplate actualError = (ErrorTemplate) response.getBody();

        ZonedDateTime expectedTimestamp = ZonedDateTime.now(ZoneId.of("Asia/Jakarta"));
        ErrorTemplate expectedError = new ErrorTemplate(expectedResponseMessage, expectedHttpStatus, expectedTimestamp);

        Assertions.assertEquals(expectedError.responseMessage(), actualError.responseMessage());
        Assertions.assertEquals(expectedError.responseCode(), actualError.responseCode());
        Assertions.assertEquals(expectedError.timestamp().truncatedTo(ChronoUnit.SECONDS), actualError.timestamp().truncatedTo(ChronoUnit.SECONDS));
    }

    @Test
    void testUserExist(){
        RuntimeException exception = new UsernameAlreadyExistsException();
        ResponseEntity<Object> response = globalExceptionHandler.userExist();
        String expectedErrorMessage = "User with the same email already exist";

        templateTestException(response, expectedErrorMessage, HttpStatus.BAD_REQUEST);
    }

    @Test
    void testUserOrReportNotExist(){
        RuntimeException exceptionUserNotExist = new UserDoesNotExistException(username);
        ResponseEntity<Object> responseUserNotExist = globalExceptionHandler.userOrReportNotExist(exceptionUserNotExist);
        String expectedErrorMessageUserNotExist = "User with username " + username + " does not exist";

        templateTestException(responseUserNotExist, expectedErrorMessageUserNotExist, HttpStatus.NOT_FOUND);

        RuntimeException exceptionReportNotExist = new ReportDoesNotExistException(reportId);
        ResponseEntity<Object> responseReportNotExist = globalExceptionHandler.userOrReportNotExist(exceptionReportNotExist);
        String expectedErrorMessageReportNotExist = "Report with id " + reportId + " does not exist";

        templateTestException(responseReportNotExist, expectedErrorMessageReportNotExist, HttpStatus.NOT_FOUND);
    }

    @Test
    void testUserIsAdministrator(){
        RuntimeException exception = new UserIsAdministratorException(username);
        ResponseEntity<Object> response = globalExceptionHandler.userIsAdministrator(exception);
        String expectedErrorMessage = "User with username " + username + " is administrator";

        templateTestException(response, expectedErrorMessage, HttpStatus.BAD_REQUEST);
    }

    @Test
    void testUserHasBeenBlocked() {
        RuntimeException exception = new UserHasBeenBlockedException(username);
        ResponseEntity<Object> response = globalExceptionHandler.userHasBeenBlocked(exception);
        String expectedErrorMessage = "User with username " + username + " has been blocked";

        templateTestException(response, expectedErrorMessage, HttpStatus.BAD_REQUEST);
    }

    @Test
    void testUserDoesNotHaveReport() {
        RuntimeException exception = new UserDoesNotHaveReportException(username);
        ResponseEntity<Object> response = globalExceptionHandler.userDoesNotHaveReport(exception);
        String expectedErrorMessage = "User with username " + username + " does not have report";

        templateTestException(response, expectedErrorMessage, HttpStatus.BAD_REQUEST);
    }

    @Test
    void testUserAndReportNotMatched() {
        RuntimeException exception = new UserAndReportNotMatchedException(username, reportId);
        ResponseEntity<Object> response = globalExceptionHandler.userAndReportNotMatched(exception);
        String expectedErrorMessage = "Report with id " + reportId + " is invalid for user with username " + username;

        templateTestException(response, expectedErrorMessage, HttpStatus.BAD_REQUEST);
    }

    @Test
    void testInvalidPassword(){
        RuntimeException exception = new InvalidPasswordException(username);
        ResponseEntity<Object> response = globalExceptionHandler.invalidPassword(exception);
        String expectedErrorMessage = "Invalid password";

        templateTestException(response, expectedErrorMessage, HttpStatus.BAD_REQUEST);
    }

    @Test
    void testInvalidToken(){
        RuntimeException exception = new InvalidTokenException();
        ResponseEntity<Object> response = globalExceptionHandler.invalidToken(exception);
        String expectedErrorMessage = "Invalid token";

        templateTestException(response, expectedErrorMessage, HttpStatus.BAD_REQUEST);
    }
}
