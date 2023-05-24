package id.ac.ui.cs.advprog.authenticationandadministration.exceptions;

import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.auth.*;
import id.ac.ui.cs.advprog.authenticationandadministration.exceptions.report.*;
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
        ResponseEntity<Object> response = globalExceptionHandler.userExist();
        String expectedErrorMessage = "User with the same username already exist";

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
        ResponseEntity<Object> response = globalExceptionHandler.exceptionBadRequest(exception);
        String expectedErrorMessage = "User with username " + username + " is administrator";

        templateTestException(response, expectedErrorMessage, HttpStatus.BAD_REQUEST);
    }

    @Test
    void testUserHasBeenBlocked() {
        RuntimeException exception = new UserHasBeenBlockedException(username);
        ResponseEntity<Object> response = globalExceptionHandler.exceptionBadRequest(exception);
        String expectedErrorMessage = "User with username " + username + " has been blocked";

        templateTestException(response, expectedErrorMessage, HttpStatus.BAD_REQUEST);
    }

    @Test
    void testUserDoesNotHaveReport() {
        RuntimeException exception = new UserDoesNotHaveReportException(username);
        ResponseEntity<Object> response = globalExceptionHandler.exceptionBadRequest(exception);
        String expectedErrorMessage = "User with username " + username + " does not have report";

        templateTestException(response, expectedErrorMessage, HttpStatus.BAD_REQUEST);
    }

    @Test
    void testUserAndReportNotMatched() {
        RuntimeException exception = new UserAndReportNotMatchedException(username, reportId);
        ResponseEntity<Object> response = globalExceptionHandler.exceptionBadRequest(exception);
        String expectedErrorMessage = "Report with id " + reportId + " is invalid for user with username " + username;

        templateTestException(response, expectedErrorMessage, HttpStatus.BAD_REQUEST);
    }

    @Test
    void testInvalidPassword(){
        RuntimeException exception = new InvalidPasswordException(username);
        ResponseEntity<Object> response = globalExceptionHandler.exceptionBadRequest(exception);
        String expectedErrorMessage = "Invalid password for user with username " + username;

        templateTestException(response, expectedErrorMessage, HttpStatus.BAD_REQUEST);
    }

    @Test
    void testInvalidToken(){
        ResponseEntity<Object> response = globalExceptionHandler.invalidToken();
        String expectedErrorMessage = "Invalid token";

        templateTestException(response, expectedErrorMessage, HttpStatus.BAD_REQUEST);
    }

    @Test
    void testDuplicateReport(){
        RuntimeException exception = new DuplicateReportException();
        ResponseEntity<Object> response = globalExceptionHandler.exceptionBadRequest(exception);
        String expectedErrorMessage = "Cannot report the same user before the admin approves the previous report";

        templateTestException(response, expectedErrorMessage, HttpStatus.BAD_REQUEST);
    }

    @Test
    void testInformationNull(){
        ResponseEntity<Object> response = globalExceptionHandler.informationNull();
        String expectedErrorMessage = "Information cannot be empty";

        templateTestException(response, expectedErrorMessage, HttpStatus.BAD_REQUEST);
    }
}
